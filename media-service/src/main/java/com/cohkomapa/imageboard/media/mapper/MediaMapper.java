package com.cohkomapa.imageboard.media.mapper;

import com.cohkomapa.imageboard.media.dto.MediaDetailsDto;
import com.cohkomapa.imageboard.media.entity.MediaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    MediaDetailsDto mapToDetailsDto(MediaEntity media);
}