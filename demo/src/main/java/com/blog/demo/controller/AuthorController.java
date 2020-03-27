package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestAuthorDTO;
import com.blog.demo.common.dto.response.ResponseAuthorDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.util.PageConverter;
import com.blog.demo.service.AuthorService;

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
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    
    @GetMapping(value = "/author")
    public ResponseBaseDTO<MyPage<ResponseAuthorDTO>> getALl(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) {
        Page<ResponseAuthorDTO> author;

        if (param != null) {
            author = authorService.search(MyPageable.convertToPageable(pageable), param);
        } else {
            author = authorService.findAll(MyPageable.convertToPageable(pageable));
        }
 
        PageConverter<ResponseAuthorDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/api/author", request.getScheme(),  request.getServerName(), request.getServerPort());
 
        String search = "";
 
        if(param != null){
            search += "&param="+param;
        }
 
        MyPage<ResponseAuthorDTO> response = converter.convert(author, url, search);
 
        return ResponseBaseDTO.ok(response);
    }

    @GetMapping(value = "/author/{id}")
    public ResponseBaseDTO<ResponseAuthorDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(authorService.findById(id));
    }
    
    @PostMapping(value="/author")
    public ResponseBaseDTO create(@RequestBody @Valid RequestAuthorDTO request) {
        authorService.save(request);
        return ResponseBaseDTO.created();
    }
    
    @PutMapping(value = "/author/{id}")
    public ResponseBaseDTO update(@PathVariable Integer id, @RequestBody @Valid RequestAuthorDTO request) {
        authorService.update(id, request);
        return ResponseBaseDTO.ok();
    }
    
    @DeleteMapping(value = "/author/{id}")
    public ResponseBaseDTO delete(@RequestParam Integer id) {
        authorService.deleteById(id);
        return ResponseBaseDTO.ok();
    }
}