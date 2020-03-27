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
 * Categories
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "categories")
@JsonIgnoreProperties(value = {"created_at", "updated_at"}, allowGetters = true)
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    @Column(length = 45, name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = true, name = "updated_at")
    @LastModifiedBy
    private Date updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categories")
    @JsonIgnore
    private Set<Blog> blog;
}