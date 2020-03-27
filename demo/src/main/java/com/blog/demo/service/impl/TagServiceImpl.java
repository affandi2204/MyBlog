package com.blog.demo.service.impl;

import java.util.Optional;

import com.blog.demo.common.dto.request.RequestTagsDTO;
import com.blog.demo.common.dto.response.ResponseTagsDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.model.Tags;
import com.blog.demo.repository.TagsRepository;
import com.blog.demo.service.TagsService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class TagServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    @Override
    public void deleteById(Integer id) {
        try {
            tagsRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseTagsDTO> findAll(Pageable pageable) {
        try {
            return tagsRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseTagsDTO findById(Integer id) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(tags);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseTagsDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return tagsRepository.findByName(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Tags save(RequestTagsDTO request) {
        try {
            Tags tags = new Tags();
            BeanUtils.copyProperties(request, tags);
         
            return tagsRepository.save(tags);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Tags update(Integer id, RequestTagsDTO request) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, tags);
            tagsRepository.save(tags);
            return tags;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseTagsDTO fromEntity(Tags tags) {
        ResponseTagsDTO response = new ResponseTagsDTO();
        BeanUtils.copyProperties(tags, response);
        return response;
    }
}