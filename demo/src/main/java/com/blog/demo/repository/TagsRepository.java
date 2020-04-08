package com.blog.demo.repository;

import com.blog.demo.model.Tags;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
/**
 * TagsRepository
 */
public interface TagsRepository extends PagingAndSortingRepository<Tags, Integer> {
	@Transactional(readOnly = true)
   	@Query("select e from #{#entityName} e where e.name like %:param% ")
	Page<Tags> search(Pageable pageable, String param);

	Tags findByName(String name);
}