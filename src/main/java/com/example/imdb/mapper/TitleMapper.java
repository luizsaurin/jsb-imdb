package com.example.imdb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.imdb.dto.title.request.CreateTitleRequestDTO;
import com.example.imdb.dto.title.response.CreateTitleResponseDTO;
import com.example.imdb.dto.title.response.FindAllTitlesResponseDTO;
import com.example.imdb.entity.TitleEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TitleMapper {

	TitleMapper INSTANCE = Mappers.getMapper(TitleMapper.class);

	TitleEntity toTitleEntity(CreateTitleRequestDTO dto);
	CreateTitleResponseDTO toCreateTitleResponseDTO(TitleEntity entity);
	FindAllTitlesResponseDTO toFindAllTitlesResponseDTO(TitleEntity entity);
}
