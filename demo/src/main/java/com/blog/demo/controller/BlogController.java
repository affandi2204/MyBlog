package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestBlogDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.dto.response.ResponseBlogDTO;
import com.blog.demo.common.util.PageConverter;
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
@RequestMapping(value = "/api")
public class BlogController {
    @Autowired
    private BlogService blogService;
    
    @GetMapping(value = "/blog")
    public ResponseBaseDTO<MyPage<ResponseBlogDTO>> getALl(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) {
        Page<ResponseBlogDTO> blog;

        if (param != null) {
            blog = blogService.search(MyPageable.convertToPageable(pageable), param);
        } else {
            blog = blogService.findAll(MyPageable.convertToPageable(pageable));
        }
 
        PageConverter<ResponseBlogDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/api/blog", request.getScheme(),  request.getServerName(), request.getServerPort());
 
        String search = "";
 
        if(param != null){
            search += "&param="+param;
        }
 
        MyPage<ResponseBlogDTO> response = converter.convert(blog, url, search);
        return ResponseBaseDTO.ok(response);
    }

    @GetMapping(value = "/blog/{id}")
    public ResponseBaseDTO<ResponseBlogDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(blogService.findById(id));
    }
    
    @PostMapping(value="/blog")
    public ResponseBaseDTO create(@RequestBody @Valid RequestBlogDTO request) {
        blogService.save(request);
        return ResponseBaseDTO.created();
    }
    
    @PutMapping(value = "/blog/{id}")
    public ResponseBaseDTO update(@PathVariable Integer id, @RequestBody @Valid RequestBlogDTO request) {
        blogService.update(id, request);
        return ResponseBaseDTO.ok();
    }
    
    @DeleteMapping(value = "/blog/{id}")
    public ResponseBaseDTO delete(@RequestParam Integer id) {
        blogService.deleteById(id);
        return ResponseBaseDTO.ok();
    }
}