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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

/**
 * Tags
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tags")
@JsonIgnoreProperties(value = {"created_at", "updated_at"}, allowGetters = true)
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Nullable
    @Column(length = 20, name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "updated_at")
    @LastModifiedBy
    private Date updated_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tags")
    private Set<BlogTags> blogTags = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Set<BlogTags> getBlogTags() {
        return blogTags;
    }

    public void setBlogTags(Set<BlogTags> blogTags) {
        this.blogTags = blogTags;
    }

	public Tags() {
    }
    
    public Tags(Integer id, String name, Date created_at, Date updated_at, Set<BlogTags> blogTags) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.blogTags = blogTags;
    }
}