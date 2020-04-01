package com.blog.demo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestAuthorDTO
 */
@Data
public class RequestBlogDTO {
    @NotNull
    @NotBlank
    private Integer authorId;

    @NotNull
    @NotBlank
    private Integer categoriesId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;
}