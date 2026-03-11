package com.example.imdb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.advice.exception.NotFoundException;
import com.example.imdb.dto.review.request.CreateReviewRequestDTO;
import com.example.imdb.dto.review.response.CreateReviewResponseDTO;
import com.example.imdb.dto.review.response.FindReviewByIdResponseDTO;
import com.example.imdb.entity.ReviewEntity;
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
		log.info("Creating review with data {}", request);

		Optional<TitleEntity> optionalTitle = titleRepository.findById(request.getTitleId());

		if (optionalTitle.isEmpty()) {
			throw new BadRequestException("Title with id [{}] not found", request.getTitleId());
		}
		
		if (reviewRepository.existsByEmailAndTitle_Id(request.getEmail(), request.getTitleId())) {
			throw new BadRequestException("This user already posted a review for title id [{}]", request.getTitleId());
		}

		return reviewMapper.toCreateReviewResponseDTO(
			reviewRepository.save(reviewMapper.toEntity(request, optionalTitle.get())));
	}

	public FindReviewByIdResponseDTO findById(Long id) {
		log.info("Searching review with id [{}]", id);
		
		Optional<ReviewEntity> optional = reviewRepository.findById(id);
		
		if (optional.isEmpty()) {
			throw new NotFoundException("Review with id [{}] not found", id);
		}
		
		return reviewMapper.toFindReviewByIdResponseDTO(optional.get());
	}
}
