package com.blog.demo.repository;

import com.blog.demo.model.Categories;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoriesRepository
 */
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{

    
}