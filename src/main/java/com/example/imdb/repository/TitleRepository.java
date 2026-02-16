package com.example.imdb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.imdb.entity.TitleEntity;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {
	
}
