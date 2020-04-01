package com.blog.demo.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * Author
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "author")
@JsonIgnoreProperties(value = {"created_at", "updated_at"},  allowGetters = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    @Column(length = 45, name = "first_name")
    private String first_name;

    @Nullable
    @Column(length = 45, name = "last_name")
    private String last_name;

    @Nullable
    @Column(length = 45, name = "username")
    private String username;

    @Nullable
    @Column(length = 150, name = "password")
    private String password;

    @Column(updatable = false, name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;

    @Column(updatable = false, name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedBy
    private Date updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIgnore
    private Set<Blog> blogs;
}