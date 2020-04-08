package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestBlogDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.dto.response.ResponseBlogDTO;
import com.blog.demo.common.util.PageConverter;
import com.blog.demo.model.Blog;
import com.blog.demo.service.BlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * CategoriesController
 */
@RestController
@RequestMapping
public class BlogController {
    @Autowired
    private BlogService blogService;
    
    @GetMapping(value = "/post")
    public ResponseBaseDTO<MyPage<ResponseBlogDTO>> getALl(
        MyPageable pageable, @RequestParam(required = false) String param, 
        @RequestParam(required = false) Integer author_id, @RequestParam(required = false) Integer categories_id,
        @RequestParam(required = false) String tag_name, HttpServletRequest request
    ) {
        Page<ResponseBlogDTO> blog;

        if (param != null) {
            blog = blogService.search(MyPageable.convertToPageable(pageable), param);
        } else if (author_id != null) {
            blog = blogService.searchByAuthor(MyPageable.convertToPageable(pageable), author_id);
        } else if (categories_id != null) {
            blog = blogService.searchByCategory(MyPageable.convertToPageable(pageable), categories_id);
        } else if (tag_name != null) {
            blog = blogService.searchByTag(MyPageable.convertToPageable(pageable), tag_name);
        } else {
            blog = blogService.findAll(MyPageable.convertToPageable(pageable));
        }
 
        PageConverter<ResponseBlogDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/post", request.getScheme(),  request.getServerName(), request.getServerPort());
 
        String search = "";
 
        if(param != null) {
            search += "&param="+param;
        } else if (author_id != null) {
            search += "&author_id="+author_id;
        } else if (categories_id != null) {
            search += "&categories_id="+categories_id;
        } else if (tag_name != null) {
            search += "&tag_name="+tag_name;
        }
 
        MyPage<ResponseBlogDTO> response = converter.convert(blog, url, search);
        return ResponseBaseDTO.ok(response);
    }

    @GetMapping(value = "/post/{id}")
    public ResponseBaseDTO<ResponseBlogDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(blogService.findById(id));
    }
    
    @PostMapping(value="/post")
    public ResponseBaseDTO create(@RequestBody @Valid RequestBlogDTO request) {
        blogService.save(request);
        return ResponseBaseDTO.created();
    }
    
    @PutMapping(value = "/post/{id}")
    public ResponseBaseDTO update(@PathVariable Integer id, @RequestBody @Valid RequestBlogDTO request) {
        blogService.update(id, request);
        return ResponseBaseDTO.ok();
    }
       
    @DeleteMapping(value = "/post")
    public ResponseBaseDTO delete(@RequestBody Blog blog) {
        blogService.deleteById(blog.getId());
        return ResponseBaseDTO.ok();
    }

    @DeleteMapping(value = "/postTags")
    public ResponseBaseDTO deleteBlogTags(@RequestBody Blog blog) {
        blogService.deleteByBlog(blog.getId());
        return ResponseBaseDTO.ok();
    }
}