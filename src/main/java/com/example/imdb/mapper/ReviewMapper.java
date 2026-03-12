package com.example.imdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.imdb.dto.review.request.CreateReviewRequestDTO;
import com.example.imdb.dto.review.request.UpdateReviewRequestDTO;
import com.example.imdb.dto.review.response.CreateReviewResponseDTO;
import com.example.imdb.dto.review.response.FindAllReviewsResponseDTO;
import com.example.imdb.dto.review.response.FindReviewByIdResponseDTO;
import com.example.imdb.dto.review.response.UpdateReviewResponseDTO;
import com.example.imdb.entity.ReviewEntity;
import com.example.imdb.entity.TitleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(source = "titleEntity", target = "title")
	ReviewEntity toEntity(CreateReviewRequestDTO dto, TitleEntity titleEntity);
	
	ReviewEntity toEntity(@MappingTarget ReviewEntity reviewEntity, UpdateReviewRequestDTO dto);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	CreateReviewResponseDTO toCreateReviewResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	FindReviewByIdResponseDTO toFindReviewByIdResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	FindAllReviewsResponseDTO toFindAllReviewsResponseDTO(ReviewEntity entity);
	
	@Mapping(source = "entity.title.id", target = "titleId")
	UpdateReviewResponseDTO toUpdateReviewResponseDTO(ReviewEntity entity);
}
