package com.blog.demo.service;

import com.blog.demo.common.dto.request.RequestAuthorDTO;
import com.blog.demo.common.dto.response.ResponseAuthorDTO;
import com.blog.demo.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AuthorService
 */
public interface AuthorService {
    Page<ResponseAuthorDTO> findAll(Pageable pageable);

    ResponseAuthorDTO findById(Integer id);

    Page<ResponseAuthorDTO> search(Pageable pageable, String param);

    Author save(RequestAuthorDTO request);

    Author update(Integer id, RequestAuthorDTO request);

    void deleteById(Integer id);
}