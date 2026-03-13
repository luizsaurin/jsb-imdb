package com.example.imdb.dto.review.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReviewResponseDTO {
	
	private Long id;
	private String email;
	private Integer rating;
	private String description;
	private Long titleId;
}
