package com.blog.demo.common.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * ResponseAuthorDTO
 */
@Data
public class ResponseBlogDTO {
    private Integer id;
    private String title;
    private String content;
    private ResponseBlogAuthorDTO author;
    private ResponseBlogCategoriesDTO categories;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date updated_at; 
}