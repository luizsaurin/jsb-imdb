package com.example.imdb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.dto.review.request.CreateReviewRequestDTO;
import com.example.imdb.dto.review.response.CreateReviewResponseDTO;
import com.example.imdb.entity.TitleEntity;
import com.example.imdb.mapper.ReviewMapper;
import com.example.imdb.repository.ReviewRepository;
import com.example.imdb.repository.TitleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

	private final TitleRepository titleRepository;
	private final ReviewMapper reviewMapper;
	private final ReviewRepository reviewRepository;
	
	public CreateReviewResponseDTO create(CreateReviewRequestDTO request) {
		log.info("[START] Creating review with data {}", request);

		Optional<TitleEntity> optionalTitle = titleRepository.findById(request.getTitleId());

		if (optionalTitle.isEmpty()) {
			String message = String.format("Could not find Title with id %s", request.getTitleId());
			log.info("[END] {}", message);
			throw new BadRequestException(message);
		}
		
		if (reviewRepository.existsByEmailAndTitle_Id(request.getEmail(), request.getTitleId())) {
			String message = String.format("User with email [%s] already posted a review for this title", 
				request.getEmail());
			log.info("[END] {}", message);
			throw new BadRequestException(message);
		}

		CreateReviewResponseDTO response = reviewMapper.toCreateReviewResponseDTO(
			reviewRepository.save(reviewMapper.toEntity(request, optionalTitle.get())));
		log.info("[END] Response: {}", response);

		return response;
	}
}
