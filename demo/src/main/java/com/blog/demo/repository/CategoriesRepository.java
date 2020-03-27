package com.blog.demo.repository;

import com.blog.demo.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoriesRepository
 */
public interface CategoriesRepository extends JpaRepository<Categories, Integer>{
	@Transactional(readOnly = true)
   	@Query("select e from #{#entityName} e where e.name like %:param% ")
	Page<Categories> findByName(Pageable pageable, String param);
}