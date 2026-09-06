package com.cohkomapa.imageboard.board.service;

import com.cohkomapa.imageboard.board.dto.post.*;
import com.cohkomapa.imageboard.board.entity.Post;
import com.cohkomapa.imageboard.board.exception.ResourceNotFoundException;
import com.cohkomapa.imageboard.board.mapper.PostMapper;
import com.cohkomapa.imageboard.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostDetailsDto createPost(PostCreateDto postCreateDto) {
        Post post = postRepository.saveAndFlush(postMapper.mapToEntity(postCreateDto));
        return postMapper.mapToDetailsDto(post);
    }

    @Transactional(readOnly = true)
    public Page<PostShortDto> getPostsByFilter(PostFilter postFilter, Pageable pageable) {
        Specification<Post> specification = PostSpecificationBuilder.buildWithFilter(postFilter);
        return postRepository.findAll(specification, pageable)
                .map(postMapper::mapToShortDto);
    }

    @Transactional(readOnly = true)
    public PostDetailsDto getById(UUID postId) {
        return postMapper.mapToDetailsDto(getEntityById(postId));
    }

    @Transactional
    public PostDetailsDto updateById(UUID postId, PostUpdateDto postUpdateDto) {
        Post post = getEntityById(postId);
        post.setTitle(postUpdateDto.title());
        post.setDescription(postUpdateDto.description());
        return postMapper.mapToDetailsDto(post);
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = getEntityById(postId);
        postRepository.delete(post);
    }

    private Post getEntityById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }
}