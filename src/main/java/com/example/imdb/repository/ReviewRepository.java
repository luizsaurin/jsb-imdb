package com.example.imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.imdb.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	
}
