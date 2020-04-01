package com.blog.demo.service;

import com.blog.demo.common.dto.request.RequestBlogDTO;
import com.blog.demo.common.dto.response.ResponseBlogDTO;
import com.blog.demo.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CategoriesService
 */
public interface BlogService {
    Page<ResponseBlogDTO> findAll(Pageable pageable);

    ResponseBlogDTO findById(Integer id);

    Page<ResponseBlogDTO> search(Pageable pageable, String param);

    Blog save(RequestBlogDTO request);

    Blog update(Integer id, RequestBlogDTO request);

    void deleteById(Integer id);
}