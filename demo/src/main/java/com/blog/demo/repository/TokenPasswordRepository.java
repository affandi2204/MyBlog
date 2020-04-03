package com.blog.demo.repository;

import com.blog.demo.model.TokenCreatePassword;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorRepository
 */
public interface TokenPasswordRepository extends JpaRepository<TokenCreatePassword, Integer> {

	TokenCreatePassword findByToken(String token);
}