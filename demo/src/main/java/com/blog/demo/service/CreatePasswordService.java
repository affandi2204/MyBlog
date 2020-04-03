package com.blog.demo.service;

import com.blog.demo.common.dto.request.RequestCreatePasswordDTO;
import com.blog.demo.common.dto.response.ResponseTokenAuthorDTO;
import com.blog.demo.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * AuthorService
 */
public interface CreatePasswordService {
    Page<ResponseTokenAuthorDTO> findAll(Pageable pageable);

    ResponseTokenAuthorDTO findByToken(String token);

    Author createPassword(String token, RequestCreatePasswordDTO request);

    Author update(Integer id, RequestCreatePasswordDTO request);
}