package com.example.imdb.dto.review.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReviewRequestDTO {
	
	@Email
	@NotBlank
	private String email;

	@Min(1)
	@Max(5)
	@NotNull
	private Integer rating;

	private String description;

	@NotNull
	private Long titleId;
}
