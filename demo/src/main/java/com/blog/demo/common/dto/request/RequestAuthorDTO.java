package com.blog.demo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestAuthorDTO
 */
@Data
public class RequestAuthorDTO {
    @NotNull
    @NotBlank
    private String first_name;

    private String last_name;

    @NotNull
    @NotBlank
    private String username;
}