package com.blog.demo.repository;

import com.blog.demo.model.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Integer>{
    @Transactional(readOnly = true)
       @Query(
        "select e from #{#entityName} e where e.title like %:param% "
       )
    Page<Blog> search(Pageable pageable, String param);
    
    @Transactional(readOnly = true)
    @Query(value = "select * from blog where blog.author_id = :author_id ", nativeQuery = true)
    Page<Blog> searchByAuthor(Pageable pageable, @Param("author_id") Integer author_id);

    @Transactional(readOnly = true)
    @Query(value = "select * from blog where blog.categories_id = :categories_id ", nativeQuery = true)
    Page<Blog> searchByCategory(Pageable pageable, @Param("categories_id") Integer categories_id);
    
    @Transactional(readOnly = true)
    @Query(value = "select blog.* from blog join blog_tags on blog.id = blog_tags.blog_id "
    +"join tags on tags.id = blog_tags.tags_id "
    +"where tags.name = :tag_name ", nativeQuery = true)
	Page<Blog> searchByTag(Pageable pageable, @Param("tag_name") String tag_name);
}