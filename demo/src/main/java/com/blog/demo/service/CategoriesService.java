package com.blog.demo.service;

import com.blog.demo.common.dto.request.RequestCategoriesDTO;
import com.blog.demo.common.dto.response.ResponseCategoriesDTO;
import com.blog.demo.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CategoriesService
 */
public interface CategoriesService {
    Page<ResponseCategoriesDTO> findAll(Pageable pageable);

    ResponseCategoriesDTO findById(Integer id);

    Page<ResponseCategoriesDTO> findByName(Pageable pageable, String param);

    Categories save(RequestCategoriesDTO request);

    Categories update(Integer id, RequestCategoriesDTO request);

    void deleteById(Integer id);
}