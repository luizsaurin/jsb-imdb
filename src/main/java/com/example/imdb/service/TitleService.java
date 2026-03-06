package com.example.imdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.advice.exception.NotFoundException;
import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.request.UpdateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.dto.title.response.FindTitleByIdResponseDTO;
import com.example.imdb.dto.title.response.UpdateTitleResponseDTO;
import com.example.imdb.entity.TitleEntity;
import com.example.imdb.mapper.TitleMapper;
import com.example.imdb.repository.TitleRepository;
import com.example.imdb.specification.TitleSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("java:S1192")
public class TitleService {

	private final TitleRepository titleRepository;
	private final TitleMapper titleMapper;
	
	@Async
	public void createMany(List<CreateTitleRequestDTO> requestList) {
		log.info("[START] Creating titles with data: {}", requestList);

		if (requestList == null || requestList.isEmpty()) {
			log.info("[END] Request list is null or empty");
			return;
		}

		List<TitleEntity> entitiesToSave = new ArrayList<>();

		requestList.forEach(item -> { 

			if (titleRepository.existsByName(item.getName())) {
				return;
			}

			entitiesToSave.add(titleMapper.toEntity(item));
		});

		
		if (entitiesToSave == null || entitiesToSave.isEmpty()) {
			log.info("[END] No title left to save");
			return;
		}
		
		titleRepository.saveAll(entitiesToSave);

		log.info("[END] {} records saved", entitiesToSave.size());
	}

	public CreateTitleResponseDTO create(CreateTitleRequestDTO request) {
		log.info("[START] Creating title with data: {}", request);

		if (titleRepository.existsByName(request.getName())) {
			String message = String.format("Title with name %s already exists", request.getName());
			log.info("[END] {}", message);
			throw new BadRequestException(message);
		}

		CreateTitleResponseDTO response = titleMapper.toCreateTitleResponseDTO(
			titleRepository.save(titleMapper.toEntity(request)));
		log.info("[END] Response: {}", response);
		
		return response;
	}
	
	public FindTitleByIdResponseDTO findById(Long id) {
		log.info("[START] Searching Title with id [{}]", id);
		
		Optional<TitleEntity> optional = titleRepository.findById(id);
		
		if (optional.isEmpty()) {
			log.info("[END] Title with id [{}] not found", id);
			throw new NotFoundException();
		}
		
		FindTitleByIdResponseDTO response = titleMapper.toFindTitleByIdResponseDTO(optional.get());
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

	public UpdateTitleResponseDTO update(Long id, UpdateTitleRequestDTO request) {
		log.info("[START] Updating Title id [{}] with data {}", id, request);

		Optional<TitleEntity> optional = titleRepository.findById(id);

		if (optional.isEmpty()) {
			log.info("[END] Title with id [{}] not found", id);
			throw new NotFoundException();
		}

		UpdateTitleResponseDTO response = titleMapper.toUpdateTitleResponseDTO(
			titleRepository.save(titleMapper.toEntity(optional.get(), request)));
		log.info("[END] Response: {}", response);
		
		return response;
	}

	public void delete(Long id) {
		log.info("[START] Deleting Title id [{}]", id);

		if (!titleRepository.existsById(id)) {
			log.info("[END] Title with id [{}] not found", id);
			throw new NotFoundException();
		}

		titleRepository.deleteById(id);
	}
	
}
