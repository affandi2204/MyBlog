package com.blog.demo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestTagsDTO
 */
@Data
public class RequestTagsDTO {
    @NotNull
    @NotBlank
    private String name;
}