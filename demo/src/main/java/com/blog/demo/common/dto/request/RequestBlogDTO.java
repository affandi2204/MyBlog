package com.blog.demo.common.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestAuthorDTO
 */
@Data
public class RequestBlogDTO {
    @NotNull
    private Integer author_id;

    @NotNull
    private Integer categories_id;

    @NotNull
    private List<String> tag_name;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;
}