package com.blog.demo.repository;

import com.blog.demo.model.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Transactional(readOnly = true)
       @Query(
        "select e from #{#entityName} e where e.first_name like %:param% OR "
        + "e.last_name like %:param% OR e.username like %:param%"
       )
	Page<Author> search(Pageable pageable, String param);
}