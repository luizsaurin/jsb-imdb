package com.example.imdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.request.UpdateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.dto.title.response.FindTitleByIdResponseDTO;
import com.example.imdb.dto.title.response.UpdateTitleResponseDTO;
import com.example.imdb.entity.TitleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleMapper {

	TitleEntity toEntity(CreateTitleRequestDTO dto);

	TitleEntity toEntity(@MappingTarget TitleEntity entity, UpdateTitleRequestDTO dto);

	CreateTitleResponseDTO toCreateTitleResponseDTO(TitleEntity entity);

	FindTitleByIdResponseDTO toFindTitleByIdResponseDTO(TitleEntity entity);

	FindAllTitlesResponseDTO toFindAllTitlesResponseDTO(TitleEntity entity);
	
	UpdateTitleResponseDTO toUpdateTitleResponseDTO(TitleEntity entity);
}
