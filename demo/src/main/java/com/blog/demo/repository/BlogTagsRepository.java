package com.blog.demo.repository;

import com.blog.demo.model.BlogTags;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BlogTagsRepository
 */
public interface BlogTagsRepository extends JpaRepository<BlogTags, Integer>{
    
}