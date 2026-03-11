package com.example.imdb.dto.review.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindAllReviewsResponseDTO {
	
	private Long id;
	private String email;
	private Integer rating;
	private String description;
	private Long titleId;
}
