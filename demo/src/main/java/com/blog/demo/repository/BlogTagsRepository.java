package com.blog.demo.repository;

import java.util.List;

import com.blog.demo.model.Blog;
import com.blog.demo.model.BlogTags;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogTagsRepository
 */
public interface BlogTagsRepository extends CrudRepository<BlogTags, Integer>{
	List<BlogTags> findByBlog(Blog blog);

	@Modifying
	@Transactional
	@Query(value = "delete from blog_tags  where blog_tags.blog_id = :blog ", nativeQuery = true)
	void deleteByBlog(@Param("blog") Integer blog);
}