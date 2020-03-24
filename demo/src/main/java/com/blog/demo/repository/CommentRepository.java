package com.blog.demo.repository;

import com.blog.demo.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CommentRepository
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>{

    
}