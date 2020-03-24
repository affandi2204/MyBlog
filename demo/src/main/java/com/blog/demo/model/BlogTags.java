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
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * BlogTags
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "blog_tags")
@JsonIgnoreProperties(value = {"created_at"}, allowGetters = true)
public class BlogTags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Tags.class)
    @MapsId("tags_id")
    @JoinColumn(name = "tags_id")
    private Tags tags;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Blog.class)
    @MapsId("blog_id")
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private Date created_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public BlogTags(Integer id, Tags tags, Blog blog, Date created_at) {
        this.id = id;
        this.tags = tags;
        this.blog = blog;
        this.created_at = created_at;
    }
}