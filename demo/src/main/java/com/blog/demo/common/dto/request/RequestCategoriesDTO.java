package com.blog.demo.common.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestCategoriesDTO
 */
@Data
public class RequestCategoriesDTO {
    @NotNull
    @NotBlank
    private String name;
}