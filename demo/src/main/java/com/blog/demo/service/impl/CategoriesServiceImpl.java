package com.blog.demo.service.impl;

import com.blog.demo.common.dto.request.RequestCategoriesDTO;
import com.blog.demo.common.dto.response.ResponseCategoriesDTO;
import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.demo.model.Categories;
import com.blog.demo.repository.CategoriesRepository;
import com.blog.demo.service.CategoriesService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CategoriesServiceImpl
 */
@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static final String RESOURCE = "Categories";
    private static final String FIELD = "id";

    @Override
    public void deleteById(Integer id) {
        try {
            categoriesRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseCategoriesDTO> findAll(Pageable pageable) {
        try {
            return categoriesRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseCategoriesDTO findById(Integer id) {
        try {
            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(categories);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseCategoriesDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return categoriesRepository.findByName(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Categories save(RequestCategoriesDTO request) {
        try {
            Categories categories = new Categories();
            BeanUtils.copyProperties(request, categories);
         
            return categoriesRepository.save(categories);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Categories update(Integer id, RequestCategoriesDTO request) {
        try {
            Categories categories = categoriesRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, categories);
            categoriesRepository.save(categories);
            return categories;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseCategoriesDTO fromEntity(Categories categories) {
        ResponseCategoriesDTO response = new ResponseCategoriesDTO();
        BeanUtils.copyProperties(categories, response);
        return response;
    }
}