package com.blog.demo.service.impl;

import com.blog.demo.common.dto.request.RequestAuthorDTO;
import com.blog.demo.common.dto.response.ResponseAuthorDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.common.util.DateTime;
import com.blog.demo.model.Author;
import com.blog.demo.repository.AuthorRepository;
import com.blog.demo.service.AuthorService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * AuthorServiceImpl
 */
@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Author";
    private static final String FIELD = "id";

    @Override
    public void deleteById(Integer id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseAuthorDTO> findAll(Pageable pageable) {
        try {
            return authorRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseAuthorDTO findById(Integer id) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(author);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseAuthorDTO> search(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return authorRepository.search(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author save(RequestAuthorDTO request) {
        try {
            Author author = new Author();
            BeanUtils.copyProperties(request, author);
         
            return authorRepository.save(author);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author update(Integer id, RequestAuthorDTO request) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, author);
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

    private ResponseAuthorDTO fromEntity(Author author) {
        ResponseAuthorDTO response = new ResponseAuthorDTO();
        BeanUtils.copyProperties(author, response);
        return response;
    }
}