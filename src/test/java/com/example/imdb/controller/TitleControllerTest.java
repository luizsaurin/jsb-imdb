package com.example.imdb.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

		when(titleService.create(any(CreateTitleRequestDTO.class))).thenReturn(responseDto);

		ResponseEntity<CreateTitleResponseDTO> response = titleController.create(requestDto);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(titleService).create(any(CreateTitleRequestDTO.class));
	}

	@Test
	void shouldCreateManyTitles() {

		List<CreateTitleRequestDTO> requestDto = List.of(
				CreateTitleRequestDTO.builder().name("Star Wars").releaseYear(1977).build(),
				CreateTitleRequestDTO.builder().name("Alien").releaseYear(1979).build());

		doNothing().when(titleService).createMany(anyList());

		ResponseEntity<Void> response = titleController.createMany(requestDto);

		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		verify(titleService).createMany(anyList());
	}

	@Test
	void shouldFindTitleById() {

		FindTitleByIdResponseDTO responseDto = FindTitleByIdResponseDTO.builder()
				.id(1L).name("Star Wars").releaseYear(1977)
				.build();

		when(titleService.findById(anyLong())).thenReturn(responseDto);

		ResponseEntity<FindTitleByIdResponseDTO> response = titleController.findById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(titleService).findById(anyLong());
	}

	@Test
	void shouldFindAllTitles() {

		FindAllTitlesResponseDTO responseDto = FindAllTitlesResponseDTO.builder()
				.id(1L).name("Star Wars")
				.build();

		Page<FindAllTitlesResponseDTO> responsePage = new PageImpl<>(List.of(responseDto));

		when(titleService.findAll(any(), anyString(), anyInt(), anyInt())).thenReturn(responsePage);

		ResponseEntity<Page<FindAllTitlesResponseDTO>> response = titleController
				.findAll("", 0, 0, null);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(titleService).findAll(any(), anyString(), anyInt(), anyInt());
	}

	@Test
	void shouldUpdateTitle() {

		UpdateTitleRequestDTO requestDto = UpdateTitleRequestDTO.builder()
				.name("Star Wars Updated")
				.build();

		UpdateTitleResponseDTO responseDto = UpdateTitleResponseDTO.builder()
				.id(1L)
				.name("Star Wars Updated")
				.build();

		when(titleService.update(anyLong(), any(UpdateTitleRequestDTO.class))).thenReturn(responseDto);

		ResponseEntity<UpdateTitleResponseDTO> response = titleController.update(1L, requestDto);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(titleService).update(anyLong(), any(UpdateTitleRequestDTO.class));
	}

	@Test
	void shouldDeleteTitle() {

		doNothing().when(titleService).delete(anyLong());

		ResponseEntity<Void> response = titleController.delete(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(titleService).delete(anyLong());
	}

}
