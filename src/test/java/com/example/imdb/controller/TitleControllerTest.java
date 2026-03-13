package com.example.imdb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.request.UpdateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.dto.title.response.FindTitleByIdResponseDTO;
import com.example.imdb.dto.title.response.UpdateTitleResponseDTO;
import com.example.imdb.service.TitleService;

@ExtendWith(MockitoExtension.class)
class TitleControllerTest {

	@InjectMocks
	private TitleController titleController;

	@Mock
	private TitleService titleService;

	@Test
	void shouldCreateTitle() {

		CreateTitleRequestDTO requestDto = CreateTitleRequestDTO.builder()
				.name("Star Wars").releaseYear(1977)
				.build();

		CreateTitleResponseDTO responseDto = CreateTitleResponseDTO.builder()
				.id(1L).name("Star Wars").releaseYear(1977)
				.build();

		when(titleService.create(requestDto)).thenReturn(responseDto);

		ResponseEntity<CreateTitleResponseDTO> response = titleController.create(requestDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(responseDto, response.getBody());
		verify(titleService).create(requestDto);
	}

	@Test
	void shouldCreateManyTitles() {

		List<CreateTitleRequestDTO> requestDto = List.of(
				CreateTitleRequestDTO.builder().name("Star Wars").releaseYear(1977).build());

		doNothing().when(titleService).createMany(requestDto);

		ResponseEntity<Void> response = titleController.createMany(requestDto);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		verify(titleService).createMany(requestDto);
	}

	@Test
	void shouldFindTitleById() {

		Long titleId = 1L;

		FindTitleByIdResponseDTO responseDto = FindTitleByIdResponseDTO.builder()
				.id(titleId).name("Star Wars").releaseYear(1977)
				.build();

		when(titleService.findById(titleId)).thenReturn(responseDto);

		ResponseEntity<FindTitleByIdResponseDTO> response = titleController.findById(titleId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(responseDto, response.getBody());
		verify(titleService).findById(titleId);
	}

	@Test
	void shouldFindAllTitles() {

		String nameParam = null;
		Integer releaseYearGteParam = null;
		Integer releaseYearLteParam = null;
		Pageable pageableParam = null;

		FindAllTitlesResponseDTO responseDto = FindAllTitlesResponseDTO.builder().id(1L).name("Star Wars").build();
		Page<FindAllTitlesResponseDTO> responsePage =new PageImpl<>(List.of(responseDto));

		when(titleService.findAll(pageableParam, nameParam, releaseYearGteParam, releaseYearLteParam))
				.thenReturn(responsePage);

		ResponseEntity<Page<FindAllTitlesResponseDTO>> response = titleController
				.findAll(nameParam, releaseYearGteParam, releaseYearLteParam, pageableParam);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(responseDto.getName(), response.getBody().getContent().get(0).getName());
		verify(titleService).findAll(pageableParam, nameParam, releaseYearGteParam, releaseYearLteParam);
	}

	@Test
	void shouldUpdateTitle() {

		Long titleId = 1L;

		UpdateTitleRequestDTO requestDto = UpdateTitleRequestDTO.builder()
				.name("Star Wars")
				.build();

		UpdateTitleResponseDTO responseDto = UpdateTitleResponseDTO.builder()
				.id(titleId)
				.name("Star Wars Updated")
				.build();

		when(titleService.update(titleId, requestDto)).thenReturn(responseDto);

		ResponseEntity<UpdateTitleResponseDTO> response = titleController.update(titleId, requestDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(responseDto, response.getBody());
		verify(titleService).update(titleId, requestDto);
	}

	@Test
	void shouldDeleteTitle() {

		Long titleId = 1L;

		doNothing().when(titleService).delete(titleId);

		ResponseEntity<Void> response = titleController.delete(titleId);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(titleService).delete(titleId);
	}

}
