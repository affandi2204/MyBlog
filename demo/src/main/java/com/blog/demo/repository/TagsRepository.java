package com.blog.demo.repository;

import java.util.List;
import java.util.Optional;

import com.blog.demo.model.Tags;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TagsRepository
 */
public interface TagsRepository extends JpaRepository<Tags, Integer>{

	List<Tags> findByName(String name);

	Optional<Tags> findById(Integer id);

	void deleteById(Integer id);   
}