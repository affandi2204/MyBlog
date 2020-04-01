package com.blog.demo.service.impl;

import com.blog.demo.common.dto.request.RequestBlogDTO;
import com.blog.demo.common.dto.response.ResponseAuthorDTO;
import com.blog.demo.common.dto.response.ResponseBlogAuthorDTO;
import com.blog.demo.common.dto.response.ResponseBlogCategoriesDTO;
import com.blog.demo.common.dto.response.ResponseBlogDTO;
import com.blog.demo.common.dto.response.ResponseCategoriesDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.common.util.DateTime;
import com.blog.demo.model.Author;
import com.blog.demo.model.Blog;
import com.blog.demo.model.Categories;
import com.blog.demo.repository.AuthorRepository;
import com.blog.demo.repository.BlogRepository;
import com.blog.demo.repository.CategoriesRepository;
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
    private DateTime dateTime;

    private static final String RESOURCE = "Blog";
    private static final String FIELD = "id";

    @Override
    public void deleteById(Integer id) {
        try {
            blogRepository.deleteById(id);
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
    public Blog save(RequestBlogDTO request) {
        try {
            Author author = authorRepository.findById(request.getAuthorId()).orElseThrow(
                ()->new ResourceNotFoundException(request.getAuthorId().toString(), "authorId", "Author") 
            );

            Categories categories = categoriesRepository.findById(request.getCategoriesId()).orElseThrow(
                ()->new ResourceNotFoundException(request.getCategoriesId().toString(), "categoriesId", "Categories") 
            );

            Blog blog = new Blog();
            BeanUtils.copyProperties(request, blog);
            blog.setAuthor(author);
            blog.setCategories(categories);
         
            return blogRepository.save(blog);
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
            Blog blog = blogRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            Author author = authorRepository.findById(request.getAuthorId()).orElseThrow(
                ()->new ResourceNotFoundException(request.getAuthorId().toString(), "authorId", "Author") 
            );

            Categories categories = categoriesRepository.findById(request.getCategoriesId()).orElseThrow(
                ()->new ResourceNotFoundException(request.getCategoriesId().toString(), "categoriesId", "Categories") 
            );

            BeanUtils.copyProperties(request, blog);
            blog.setAuthor(author);
            blog.setCategories(categories);
            blog.setUpdated_at(dateTime.getCurrentDate());
            blogRepository.save(blog);
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
        return response;
    }
}