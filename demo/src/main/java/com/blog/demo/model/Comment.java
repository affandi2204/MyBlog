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
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

/**
 * Comment
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
@JsonIgnoreProperties(value = {"created_at", "updated_at"}, allowGetters = true)    
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Blog.class)
    @MapsId("blog_id")
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Nullable
    @Column(length = 80, name = "guest_email")
    private String guest_email;

    @Nullable
    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "created_at")
    @CreatedDate
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, name = "updated_at")
    @LastModifiedBy
    private Date updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public String getGuest_email() {
        return guest_email;
    }

    public void setGuest_email(String guest_email) {
        this.guest_email = guest_email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Comment(Integer id, Blog blog, String guest_email, String content, Date created_at, Date updated_at) {
        this.id = id;
        this.blog = blog;
        this.guest_email = guest_email;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}