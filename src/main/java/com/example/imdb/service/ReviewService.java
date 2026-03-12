package com.example.imdb.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.imdb.advice.exception.BadRequestException;
import com.example.imdb.advice.exception.NotFoundException;
import com.example.imdb.dto.review.request.CreateReviewRequestDTO;
import com.example.imdb.dto.review.request.UpdateReviewRequestDTO;
import com.example.imdb.dto.review.response.CreateReviewResponseDTO;
import com.example.imdb.dto.review.response.FindAllReviewsResponseDTO;
import com.example.imdb.dto.review.response.FindReviewByIdResponseDTO;
import com.example.imdb.dto.review.response.UpdateReviewResponseDTO;
import com.example.imdb.entity.ReviewEntity;
import com.example.imdb.entity.TitleEntity;
import com.example.imdb.mapper.ReviewMapper;
import com.example.imdb.repository.ReviewRepository;
import com.example.imdb.repository.TitleRepository;
import com.example.imdb.specification.ReviewSpecifications;

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

	public Page<FindAllReviewsResponseDTO> findAll(Pageable pageable, Long titleId, Integer ratingGte, 
		Integer ratingLte) {
		log.info("Searching reviews with {} and params titleId [{}], ratingGte [{}], ratingLte [{}]", 
		pageable, titleId, ratingGte, ratingLte);

		Specification<ReviewEntity> spec = Specification.allOf(
			ReviewSpecifications.titleIdEquals(titleId),
			ReviewSpecifications.ratingGte(ratingGte),
			ReviewSpecifications.ratingLte(ratingLte)
		);

		Page<FindAllReviewsResponseDTO> page = reviewRepository.findAll(spec, pageable)
			.map(reviewMapper::toFindAllReviewsResponseDTO);
		
		log.info("[{}] records found", page.getTotalElements());

		return page;
	}

	public UpdateReviewResponseDTO update(Long id, UpdateReviewRequestDTO request) {
		log.info("Updating review with id [{}] using data {}", id, request);

		Optional<ReviewEntity> optional = reviewRepository.findById(id);

		if (optional.isEmpty()) {
			throw new NotFoundException("Review with id [{}] not found", id);
		}

		return reviewMapper.toUpdateReviewResponseDTO(
			reviewRepository.save(reviewMapper.toEntity(optional.get(), request)));
	}

	public void delete(Long id) {
		log.info("Deleting review id [{}]", id);

		if (!reviewRepository.existsById(id)) {
			throw new NotFoundException("Review with id [{}] not found", id);
		}

		reviewRepository.deleteById(id);
	}
}
