package com.example.imdb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.imdb.constant.URIs;
import com.example.imdb.dto.review.request.CreateReviewRequestDTO;
import com.example.imdb.dto.review.response.CreateReviewResponseDTO;
import com.example.imdb.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping(URIs.CREATE_REVIEW)
	public ResponseEntity<CreateReviewResponseDTO> create(@RequestBody @Valid CreateReviewRequestDTO request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(request));
	}

}
