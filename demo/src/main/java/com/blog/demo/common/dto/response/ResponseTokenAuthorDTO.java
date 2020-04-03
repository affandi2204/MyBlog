package com.blog.demo.common.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * ResponseAuthorDTO
 */
@Data
public class ResponseTokenAuthorDTO {
    private Integer id;
    private Integer author_id;
    private String username;
    private String token;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;
}