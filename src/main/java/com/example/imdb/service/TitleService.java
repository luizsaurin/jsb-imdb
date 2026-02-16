package com.example.imdb.service;

import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.entity.TitleEntity;
import com.example.imdb.mapper.TitleMapper;
import com.example.imdb.repository.TitleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TitleService {

	private final TitleRepository titleRepository;
	private final TitleMapper titleMapper;
	
	public CreateTitleResponseDTO create(CreateTitleRequestDTO request) {
		log.info("[START] Creating title with data: {}", request);

		if (titleRepository.existsByName(request.getName())) {
			String message = String.format("Title with name %s already exists", request.getName());
			log.info("[END] {}", message);
			throw new BadRequestException(message);
		}

		TitleEntity newEntity = titleMapper.toTitleEntity(request);
		log.info("[INFO] Created entity: {}", newEntity);
		
		TitleEntity savedEntity = titleRepository.save(newEntity);
		log.info("[INFO] Saved entity: {}", newEntity);

		CreateTitleResponseDTO response = titleMapper.toCreateTitleResponseDTO(savedEntity);
		log.info("[END] Response dto: {}", response);

		return response;
	}
}
