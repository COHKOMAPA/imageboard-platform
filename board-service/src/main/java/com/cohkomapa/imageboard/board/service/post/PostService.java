package com.cohkomapa.imageboard.board.service.post;

import com.cohkomapa.imageboard.board.dto.post.*;
import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import com.cohkomapa.imageboard.board.exception.ResourceNotFoundException;
import com.cohkomapa.imageboard.board.mapper.PostMapper;
import com.cohkomapa.imageboard.board.repository.PostRepository;
import com.cohkomapa.imageboard.board.validator.media.MediaValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MediaValidator mediaValidator;
    private final PostCreationService postCreationService;

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    // No @Transactional to avoid holding a DB transaction during a network call
    public PostDetailsDto createPost(PostCreateDto postCreateDto) {
        mediaValidator.validateMedia(new HashSet<>(postCreateDto.mediaIds()));
        return postCreationService.createPost(postCreateDto);
    }

    @Transactional(readOnly = true)
    public Page<PostShortDto> getPostsByFilter(PostFilter postFilter, Pageable pageable) {
        Specification<PostEntity> specification = PostSpecificationBuilder.buildWithFilter(postFilter);
        return postRepository.findAll(specification, pageable)
                .map(postMapper::mapToShortDto);
    }

    @Transactional(readOnly = true)
    public PostDetailsDto getById(UUID postId) {
        return postMapper.mapToDetailsDto(getEntityById(postId));
    }

    @Transactional
    public PostDetailsDto updateById(UUID postId, PostUpdateDto postUpdateDto) {
        PostEntity post = getEntityById(postId);
        post.setTitle(postUpdateDto.title());
        post.setDescription(postUpdateDto.description());
        return postMapper.mapToDetailsDto(post);
    }

    @Transactional
    public void deletePost(UUID postId) {
        PostEntity post = getEntityById(postId);
        postRepository.delete(post);
    }

    private PostEntity getEntityById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));
    }
}