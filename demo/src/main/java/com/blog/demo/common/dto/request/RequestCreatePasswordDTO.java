package com.blog.demo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestAuthorDTO
 */
@Data
public class RequestCreatePasswordDTO {
    @NotNull
    @NotBlank
    private String password;
}