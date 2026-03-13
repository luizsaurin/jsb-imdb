package com.example.imdb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.service.TitleService;

@ExtendWith(MockitoExtension.class)
class TitleControllerTest {
	
	@InjectMocks
	private TitleController titleController;

    @Mock
    private TitleService titleService;

	@Test
	void shouldCreateTitle() {

		CreateTitleRequestDTO request = CreateTitleRequestDTO.builder()
			.name("Star Wars")
			.releaseYear(1977)
		.build();

		CreateTitleResponseDTO expectedResponse = CreateTitleResponseDTO.builder()
			.id(1L)
			.name("Star Wars")
			.releaseYear(1977)
		.build();

		when(titleService.create(any(CreateTitleRequestDTO.class))).thenReturn(expectedResponse);

		ResponseEntity<CreateTitleResponseDTO> actualResponse = titleController.create(request);

		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(expectedResponse, actualResponse.getBody());
		verify(titleService).create(request);
	}
}
