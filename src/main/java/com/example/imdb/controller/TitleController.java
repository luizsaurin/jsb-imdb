package com.example.imdb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.imdb.constant.URIs;
import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
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
}
