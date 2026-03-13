package com.example.imdb.dto.title.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateTitleResponseDTO {
	
	private Long id;
	private String name;
	private Integer releaseYear;
}
