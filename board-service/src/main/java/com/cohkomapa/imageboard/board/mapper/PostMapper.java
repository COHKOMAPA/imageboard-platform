package com.cohkomapa.imageboard.board.mapper;

import com.cohkomapa.imageboard.board.dto.post.PostCreateDto;
import com.cohkomapa.imageboard.board.dto.post.PostDetailsDto;
import com.cohkomapa.imageboard.board.dto.post.PostShortDto;
import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "media", ignore = true)
    PostEntity mapToEntity(PostCreateDto createDto);

    PostShortDto mapToShortDto(PostEntity post);

    PostDetailsDto mapToDetailsDto(PostEntity post);
}