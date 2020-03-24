package com.blog.demo.repository;

import com.blog.demo.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>{

    
}