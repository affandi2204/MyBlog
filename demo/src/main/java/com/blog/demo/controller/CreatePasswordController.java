package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestAuthorDTO;
import com.blog.demo.common.dto.request.RequestCreatePasswordDTO;
import com.blog.demo.common.dto.response.ResponseAuthorDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.dto.response.ResponseTokenAuthorDTO;
import com.blog.demo.common.util.PageConverter;
import com.blog.demo.service.AuthorService;
import com.blog.demo.service.CreatePasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * CategoriesController
 */
@RestController
@RequestMapping
public class CreatePasswordController {
    @Autowired
    private CreatePasswordService createPasswordService;

    // @GetMapping(value = "/author/{id}")
    // public ResponseBaseDTO<ResponseAuthorDTO> getOne(@PathVariable Integer id) {
    //     return ResponseBaseDTO.ok(authorService.findById(id));
    // }
    
    @GetMapping(value = "/token-create-password")
    public ResponseBaseDTO<MyPage<ResponseTokenAuthorDTO>> getALl(
        MyPageable pageable, HttpServletRequest request
    ) {
        Page<ResponseTokenAuthorDTO> author;

        author = createPasswordService.findAll(MyPageable.convertToPageable(pageable));

        PageConverter<ResponseTokenAuthorDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/token-create-password", request.getScheme(),  request.getServerName(), request.getServerPort());
 
        String search = "";

        MyPage<ResponseTokenAuthorDTO> response = converter.convert(author, url, search);
        return ResponseBaseDTO.ok(response);
    }

    @PutMapping(value="/create-password/{token}")
    public ResponseBaseDTO create(@PathVariable String token, @RequestBody @Valid RequestCreatePasswordDTO request) {
        createPasswordService.createPassword(token, request);
        return ResponseBaseDTO.created();
    }
    
    // @PutMapping(value = "/author/{id}")
    // public ResponseBaseDTO update(@PathVariable Integer id, @RequestBody @Valid RequestCreatePasswordDTO request) {
    //     authorService.update(id, request);
    //     return ResponseBaseDTO.ok();
    // }
}