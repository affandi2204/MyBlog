package com.blog.demo.repository;

import com.blog.demo.model.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>{

    
}