package com.example.imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.imdb.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, JpaSpecificationExecutor<ReviewEntity> {
	
	boolean existsByEmailAndTitle_Id(String email, Long titleId);
}
