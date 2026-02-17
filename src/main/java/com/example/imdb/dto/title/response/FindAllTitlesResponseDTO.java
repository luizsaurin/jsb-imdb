package com.example.imdb.dto.title.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindAllTitlesResponseDTO {

	private Long id;
	private String name;
	private Integer releaseYear;
}
