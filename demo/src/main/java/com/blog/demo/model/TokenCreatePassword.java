package com.blog.demo.model;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * BlogTags
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "token_create_password")
@JsonIgnoreProperties(value = {"created_at"}, allowGetters = true)
public class TokenCreatePassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private Author author;

    @Column(length = 255, name = "token")
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private Date created_at;
}