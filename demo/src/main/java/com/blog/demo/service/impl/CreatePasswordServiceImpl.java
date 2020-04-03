package com.blog.demo.service.impl;

import com.blog.demo.common.dto.request.RequestCreatePasswordDTO;
import com.blog.demo.common.dto.response.ResponseTokenAuthorDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.common.util.DateTime;
import com.blog.demo.model.Author;
import com.blog.demo.model.TokenCreatePassword;
import com.blog.demo.repository.AuthorRepository;
import com.blog.demo.repository.TokenPasswordRepository;
import com.blog.demo.service.CreatePasswordService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthorServiceImpl
 */
@Slf4j
@Service
public class CreatePasswordServiceImpl implements CreatePasswordService {
    
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TokenPasswordRepository tokenPasswordRepository;

    @Autowired
    private DateTime dateTime;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final String RESOURCE = "Create Password";
    private static final String FIELD = "id";

    @Override
    public Page<ResponseTokenAuthorDTO> findAll(Pageable pageable) {
        try {
            return tokenPasswordRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseTokenAuthorDTO findByToken(String token) {
        try {
            TokenCreatePassword tokenCreatePassword =  tokenPasswordRepository.findByToken(token);

            return fromEntity(tokenCreatePassword);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author createPassword(String token, RequestCreatePasswordDTO request) {
        try {
            TokenCreatePassword tokenCreatePassword =  tokenPasswordRepository.findByToken(token);

            Author author = authorRepository.findById(fromEntity(tokenCreatePassword).getAuthor_id()).orElseThrow(
                ()->new ResourceNotFoundException(fromEntity(tokenCreatePassword).getAuthor_id().toString(), "author_id", "Author")
            );   
            
            BeanUtils.copyProperties(request, author);
            author.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return author;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author update(Integer id, RequestCreatePasswordDTO request) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, author);
            author.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            author.setUpdated_at(dateTime.getCurrentDate());
            authorRepository.save(author);
            return author;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseTokenAuthorDTO fromEntity(TokenCreatePassword tokenCreatePassword) {
        ResponseTokenAuthorDTO response = new ResponseTokenAuthorDTO();
        BeanUtils.copyProperties(tokenCreatePassword, response);
        response.setAuthor_id(tokenCreatePassword.getAuthor().getId());
        response.setUsername(tokenCreatePassword.getAuthor().getUsername());
        return response;
    }
}