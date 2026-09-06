package com.cohkomapa.imageboard.board.mapper;

import com.cohkomapa.imageboard.board.dto.post.PostCreateDto;
import com.cohkomapa.imageboard.board.dto.post.PostDetailsDto;
import com.cohkomapa.imageboard.board.dto.post.PostShortDto;
import com.cohkomapa.imageboard.board.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post mapToEntity(PostCreateDto createDto);

    PostShortDto mapToShortDto(Post post);

    PostDetailsDto mapToDetailsDto(Post post);
}