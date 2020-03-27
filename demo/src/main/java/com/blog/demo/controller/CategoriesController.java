package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestCategoriesDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.dto.response.ResponseCategoriesDTO;
import com.blog.demo.common.util.PageConverter;
import com.blog.demo.service.CategoriesService;

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
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;
    
    @GetMapping(value = "/categories")
    public ResponseBaseDTO<MyPage<ResponseCategoriesDTO>> getALl(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) {
        Page<ResponseCategoriesDTO> categories;

        if (param != null) {
            categories = categoriesService.findByName(MyPageable.convertToPageable(pageable), param);
        } else {
            categories = categoriesService.findAll(MyPageable.convertToPageable(pageable));
        }
 
        PageConverter<ResponseCategoriesDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/api/categories",request.getScheme(),  request.getServerName(), request.getServerPort());
 
        String search = "";
 
        if(param != null){
            search += "&param="+param;
        }
 
        MyPage<ResponseCategoriesDTO> response = converter.convert(categories, url, search);
 
        return ResponseBaseDTO.ok(response);
    }

    @GetMapping(value = "/categories/{id}")
    public ResponseBaseDTO<ResponseCategoriesDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(categoriesService.findById(id));
    }
    
    @PostMapping(value="/categories")
    public ResponseBaseDTO create(@RequestBody @Valid RequestCategoriesDTO request) {
        categoriesService.save(request);
        return ResponseBaseDTO.created();
    }
    
    @PutMapping(value = "/categories/{id}")
    public ResponseBaseDTO update(@PathVariable Integer id, @RequestBody @Valid RequestCategoriesDTO request) {
        categoriesService.update(id, request);
        return ResponseBaseDTO.ok();
    }
    
    @DeleteMapping(value = "/categories/{id}")
    public ResponseBaseDTO delete(@RequestParam Integer id) {
        categoriesService.deleteById(id);
        return ResponseBaseDTO.ok();
    }
    
}