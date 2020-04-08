package com.blog.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.blog.demo.common.dto.request.RequestBlogDTO;
import com.blog.demo.common.dto.response.ResponseBTagsIdDTO;
import com.blog.demo.common.dto.response.ResponseBlogAuthorDTO;
import com.blog.demo.common.dto.response.ResponseBlogCategoriesDTO;
import com.blog.demo.common.dto.response.ResponseBlogDTO;
import com.blog.demo.common.dto.response.ResponseBlogTagsDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.common.util.DateTime;
import com.blog.demo.model.Author;
import com.blog.demo.model.Blog;
import com.blog.demo.model.BlogTags;
import com.blog.demo.model.Categories;
import com.blog.demo.model.Tags;
import com.blog.demo.repository.AuthorRepository;
import com.blog.demo.repository.BlogRepository;
import com.blog.demo.repository.BlogTagsRepository;
import com.blog.demo.repository.CategoriesRepository;
import com.blog.demo.repository.TagsRepository;
import com.blog.demo.service.BlogService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CategoriesServiceImpl
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private CategoriesRepository categoriesRepository;
    
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private BlogTagsRepository blogTagsRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Blog";
    private static final String FIELD = "id";

    @Override
    public void deleteById(Integer id) {
        try {
            blogTagsRepository.deleteByBlog(id);
            blogRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteByBlog(Integer blog) {
        try {
            blogTagsRepository.deleteByBlog(blog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> findAll(Pageable pageable) {
        try {
            return blogRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseBlogDTO findById(Integer id) {
        try {
            Blog blog = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(blog);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> search(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return blogRepository.search(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> searchByAuthor(Pageable pageable, Integer author_id) {
        try {
            return blogRepository.searchByAuthor(pageable, author_id).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> searchByCategory(Pageable pageable, Integer categories_id) {
        try {
            return blogRepository.searchByCategory(pageable, categories_id).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> searchByTag(Pageable pageable, String tag_name) {
        try {
            return blogRepository.searchByTag(pageable, tag_name).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Blog save(RequestBlogDTO request) {
        try {
            Author author = authorRepository.findById(request.getAuthor_id())
                .orElseThrow(
                    () -> new ResourceNotFoundException(request.getAuthor_id().toString(), "authorId", "Author")
                );

            Categories categories = categoriesRepository.findById(request.getCategories_id())
                .orElseThrow(
                    () -> new ResourceNotFoundException(request.getCategories_id().toString(), "categoriesId", "Categories")
                );

            ArrayList<Tags> tags = new ArrayList<Tags>();

            for (String tag : request.getTag_name()) {
                Tags tag_list = tagsRepository.findByName(tag);

                if (tag_list == null) {
                    Tags new_tag = new Tags();

                    new_tag.setName(tag);
                    tagsRepository.save(new_tag);

                    Tags tag_id = tagsRepository.findById(new_tag.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(new_tag.getId().toString(), "id", "Tags")
                    );

                    tags.add(tag_id);
                } else {
                    tags.add(tag_list);
                }
            }

            Blog blog = new Blog();
            BeanUtils.copyProperties(request, blog);
            blog.setAuthor(author);
            blog.setCategories(categories);

            blogRepository.save(blog);

            for (Tags tag : tags) {
                BlogTags blogTags = new BlogTags();
                blogTags.setBlog(blog);
                blogTags.setTags(tag);    
                blogTagsRepository.save(blogTags);
            }

            return blog;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Blog update(Integer id, RequestBlogDTO request) {
        try {
            Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            Categories categories = categoriesRepository.findById(request.getCategories_id())
                .orElseThrow(
                    () -> new ResourceNotFoundException(request.getCategories_id().toString(), "categoriesId", "Categories")
                );

            ArrayList<Tags> tags = new ArrayList<Tags>();

            for (String tag : request.getTag_name()) {
                Tags tag_list = tagsRepository.findByName(tag);

                if (tag_list == null) {
                    Tags new_tag = new Tags();

                    new_tag.setName(tag);
                    tagsRepository.save(new_tag);

                    Tags tag_id = tagsRepository.findById(new_tag.getId()).orElseThrow(
                        () -> new ResourceNotFoundException(new_tag.getId().toString(), "id", "Tags")
                    );

                    tags.add(tag_id);
                } else {
                    tags.add(tag_list);
                }
            }

            BeanUtils.copyProperties(request, blog);
            blog.setCategories(categories);
            blog.setUpdated_at(dateTime.getCurrentDate());
            blogRepository.save(blog);

            blogTagsRepository.deleteByBlog(blog.getId());

            for (Tags tag : tags) {
                BlogTags blogTag = new BlogTags();
                blogTag.setBlog(blog);
                blogTag.setTags(tag);    
                blogTagsRepository.save(blogTag);
            }


            return blog;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseBlogDTO fromEntity(Blog blog) {
        ResponseBlogDTO response = new ResponseBlogDTO();
        BeanUtils.copyProperties(blog, response);
        response.setCategories(new ResponseBlogCategoriesDTO());
        response.getCategories().setId(blog.getCategories().getId());
        response.getCategories().setName(blog.getCategories().getName());

        response.setAuthor(new ResponseBlogAuthorDTO());
        response.getAuthor().setId(blog.getAuthor().getId());
        response.getAuthor().setFirst_name(blog.getAuthor().getFirst_name());
        response.getAuthor().setLast_name(blog.getAuthor().getLast_name());

        List<BlogTags> blogTags = blogTagsRepository.findByBlog(blog);

        if (!blogTags.isEmpty()) {
            ArrayList<ResponseBlogTagsDTO> blog_tag_list = convertToBlog(blogTags);
            response.setTags(blog_tag_list);
        }

        return response;
    }

    private ArrayList<ResponseBlogTagsDTO> convertToBlog(List<BlogTags> blogTags) {
        ArrayList<ResponseBlogTagsDTO> blog_tag_list = new ArrayList<>();

        for (BlogTags tag : blogTags) {
            ResponseBlogTagsDTO blog_tag_temp = new ResponseBlogTagsDTO();
             BeanUtils.copyProperties(tag, blog_tag_temp);

             blog_tag_temp.setId(tag.getTags().getId());
             blog_tag_temp.setName(tag.getTags().getName());

             blog_tag_list.add(blog_tag_temp);
        }

        return blog_tag_list;
    }
}