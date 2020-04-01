package com.blog.demo.repository;

import com.blog.demo.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>{
    @Transactional(readOnly = true)
       @Query(
        "select e from #{#entityName} e where e.title like %:param% OR "
        + "e.content like %:param%"
       )
	Page<Blog> search(Pageable pageable, String param);    
}