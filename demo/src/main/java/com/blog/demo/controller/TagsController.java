package com.blog.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.demo.common.dto.MyPage;
import com.blog.demo.common.dto.MyPageable;
import com.blog.demo.common.dto.request.RequestTagsDTO;
import com.blog.demo.common.dto.response.ResponseBaseDTO;
import com.blog.demo.common.dto.response.ResponseTagsDTO;
import com.blog.demo.common.util.PageConverter;
import com.blog.demo.service.TagsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthorController
 */
@RestController
@RequestMapping(path = "/api")
public class TagsController {
    
    @Autowired
    private TagsService tagsService; 

    @GetMapping(value = "/tags")
    public ResponseBaseDTO<MyPage<ResponseTagsDTO>> listTags(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
       Page<ResponseTagsDTO> tags;

       if (param != null) {
           tags = tagsService.findByName(MyPageable.convertToPageable(pageable), param);
       } else {
           tags = tagsService.findAll(MyPageable.convertToPageable(pageable));
       }

       PageConverter<ResponseTagsDTO> converter = new PageConverter<>();
       String url = String.format("%s://%s:%d/api/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

       String search = "";

       if(param != null){
           search += "&param="+param;
       }

       MyPage<ResponseTagsDTO> response = converter.convert(tags, url, search);

       return ResponseBaseDTO.ok(response);
    }

    @GetMapping(value = "/tags/{id}")
    public ResponseBaseDTO<ResponseTagsDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(tagsService.findById(id));
    }

    @PostMapping(value = "/tags")
    public ResponseBaseDTO createTag(@ModelAttribute @Valid RequestTagsDTO request) {
        tagsService.save(request);
        return ResponseBaseDTO.created();
    }

    @DeleteMapping(value = "/tags/{id}")
    public ResponseBaseDTO deleteTag(@PathVariable(value = "id") Integer id) {
       tagsService.deleteById(id);
       return ResponseBaseDTO.ok();
    }

    @PutMapping(value = "/tags/{id}")
    public ResponseBaseDTO updateTag(
        @ModelAttribute @Valid RequestTagsDTO request, @PathVariable("id") Integer id
    ) {
       tagsService.update(id, request);
       return ResponseBaseDTO.ok();
    }
}