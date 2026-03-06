package com.example.imdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.DuplicatedResourceException;
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
		log.info("Creating titles with data: {}", requestList);

		if (requestList == null || requestList.isEmpty()) {
			log.info("Request list is null or empty");
			return;
		}

		List<TitleEntity> entitiesToSave = new ArrayList<>();

		requestList.forEach(item -> { 

			if (titleRepository.existsByName(item.getName())) return;

			entitiesToSave.add(titleMapper.toEntity(item));
		});

		
		if (entitiesToSave == null || entitiesToSave.isEmpty()) {
			log.info("No title left to save");
			return;
		}
		
		titleRepository.saveAll(entitiesToSave);

		log.info("[{}] records saved", entitiesToSave.size());
	}

	public CreateTitleResponseDTO create(CreateTitleRequestDTO request) {
		log.info("Creating title with data: {}", request);

		if (titleRepository.existsByName(request.getName())) {
			log.info("Title with name [{}] already exists", request.getName());
			throw new DuplicatedResourceException();
		}

		return titleMapper.toCreateTitleResponseDTO(titleRepository.save(titleMapper.toEntity(request)));
	}
	
	public FindTitleByIdResponseDTO findById(Long id) {
		log.info("Searching title with id [{}]", id);
		
		Optional<TitleEntity> optional = titleRepository.findById(id);
		
		if (optional.isEmpty()) {
			log.info("Title with id [{}] not found", id);
			throw new NotFoundException();
		}
		
		return titleMapper.toFindTitleByIdResponseDTO(optional.get());
	}
	
	public Page<FindAllTitlesResponseDTO> findAll(Pageable pageable, String name, Integer releaseYearGte, 
		Integer releaseYearLte) {
		log.info("Searching Titles with {} and params name [{}], releaseYearGte [{}], releaseYearLte [{}]", 
		pageable, name, releaseYearGte, releaseYearLte);

		Specification<TitleEntity> spec = Specification.allOf(
			TitleSpecifications.nameContains(name),
			TitleSpecifications.releaseYearGte(releaseYearGte),
			TitleSpecifications.releaseYearLte(releaseYearLte)
		);

		Page<FindAllTitlesResponseDTO> page = titleRepository.findAll(spec, pageable)
			.map(titleMapper::toFindAllTitlesResponseDTO);
		
		log.info("[{}] records found", page.getTotalElements());

		return page;
	}

	public UpdateTitleResponseDTO update(Long id, UpdateTitleRequestDTO request) {
		log.info("Updating title with id [{}] using data {}", id, request);

		Optional<TitleEntity> optional = titleRepository.findById(id);

		if (optional.isEmpty()) {
			log.info("Title with id [{}] not found", id);
			throw new NotFoundException();
		}

		return titleMapper.toUpdateTitleResponseDTO(
			titleRepository.save(titleMapper.toEntity(optional.get(), request)));
	}

	public void delete(Long id) {
		log.info("Deleting Title id [{}]", id);

		if (!titleRepository.existsById(id)) {
			log.info("Title with id [{}] not found", id);
			throw new NotFoundException();
		}

		titleRepository.deleteById(id);
	}
	
}
