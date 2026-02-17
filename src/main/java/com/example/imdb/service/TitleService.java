package com.example.imdb.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.advice.exception.NotFoundException;
import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.entity.TitleEntity;
import com.example.imdb.mapper.TitleMapper;
import com.example.imdb.repository.TitleRepository;
import com.example.imdb.specification.TitleSpecifications;

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
		log.info("[END] Response: {}", response);
		
		return response;
	}
	
	public CreateTitleResponseDTO findById(Long id) {
		log.info("[START] Searching Title with id [{}]", id);
		
		Optional<TitleEntity> optional = titleRepository.findById(id);
		
		if (optional.isEmpty()) {
			log.info("[END] Title with id [{}] not found", id);
			throw new NotFoundException();
		}
		
		TitleEntity titleEntity = optional.get();
		log.info("[INFO] Found entity: {}", titleEntity);
		
		CreateTitleResponseDTO response = titleMapper.toCreateTitleResponseDTO(titleEntity);
		log.info("[END] Response: {}", response);
		
		return response;
	}
	
	public Page<FindAllTitlesResponseDTO> findAll(Pageable pageable, String name, Integer releaseYearGte, 
		Integer releaseYearLte) {
		log.info("[START] Searching Titles with {} and params name [{}], releaseYearGte [{}], releaseYearLte [{}]", 
		pageable, name, releaseYearGte, releaseYearLte);

		Specification<TitleEntity> spec = Specification.allOf(
			TitleSpecifications.nameContains(name),
			TitleSpecifications.releaseYearGte(releaseYearGte),
			TitleSpecifications.releaseYearLte(releaseYearLte)
		);

		Page<FindAllTitlesResponseDTO> page = titleRepository.findAll(spec, pageable)
			.map(titleMapper::toFindAllTitlesResponseDTO);
		
		log.info("[END] Found {} records", page.getTotalElements());

		return page;
	}
	
}
