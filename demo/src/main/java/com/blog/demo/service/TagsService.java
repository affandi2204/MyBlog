package com.blog.demo.service;

import com.blog.demo.common.dto.request.RequestTagsDTO;
import com.blog.demo.common.dto.response.ResponseTagsDTO;
import com.blog.demo.model.Tags;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TagsService
 */
public interface TagsService {
    Page<ResponseTagsDTO> findAll(Pageable pageable);

    ResponseTagsDTO findById(Integer id);

    Page<ResponseTagsDTO> search(Pageable pageable, String param);

    ResponseTagsDTO findByName(String name);

    Tags save(RequestTagsDTO request);

    Tags update(Integer id, RequestTagsDTO request);

    void deleteById(Integer id);
}