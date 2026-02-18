package com.example.imdb.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.imdb.constant.URIs;
import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.request.UpdateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.dto.title.response.UpdateTitleResponseDTO;
import com.example.imdb.service.TitleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TitleController {

	private final TitleService titleService;
	
	@PostMapping(URIs.CREATE_TITLE)
	public ResponseEntity<CreateTitleResponseDTO> create(@RequestBody @Valid CreateTitleRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(titleService.create(request));
	}

	@PostMapping(URIs.CREATE_TITLES)
	public ResponseEntity<Void> createMany(@RequestBody @Valid List<CreateTitleRequestDTO> request) {
		titleService.createMany(request);
		return ResponseEntity.accepted().build();
	}

	@GetMapping(URIs.FIND_TITLE_BY_ID)
	public ResponseEntity<CreateTitleResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(titleService.findById(id));
	}

	@GetMapping(URIs.FIND_ALL_TITLES)
	public ResponseEntity<Page<FindAllTitlesResponseDTO>> findAll(
		@RequestParam(required = false) String name,
		@RequestParam(required = false) Integer releaseYearGte,
		@RequestParam(required = false) Integer releaseYearLte,
		@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(titleService.findAll(pageable, name, releaseYearGte, releaseYearLte));
	}

	@PutMapping(URIs.UPDATE_TITLE)
	public ResponseEntity<UpdateTitleResponseDTO> update(
		@PathVariable Long id, @RequestBody UpdateTitleRequestDTO request) {
		return ResponseEntity.ok(titleService.update(id, request));
	}

	@DeleteMapping(URIs.DELETE_TITLE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		titleService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
