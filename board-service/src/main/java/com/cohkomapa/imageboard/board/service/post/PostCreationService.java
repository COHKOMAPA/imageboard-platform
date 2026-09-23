package com.cohkomapa.imageboard.board.service.post;

import com.cohkomapa.imageboard.board.dto.post.PostCreateDto;
import com.cohkomapa.imageboard.board.dto.post.PostDetailsDto;
import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import com.cohkomapa.imageboard.board.mapper.PostMapper;
import com.cohkomapa.imageboard.board.mapper.PostMediaMapper;
import com.cohkomapa.imageboard.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCreationService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;
    private final PostMediaMapper postMediaMapper;

    @Transactional
    public PostDetailsDto createPost(PostCreateDto postCreateDto) {
        PostEntity post = postMapper.mapToEntity(postCreateDto);
        attachMedia(post, postCreateDto.mediaIds());
        post = postRepository.saveAndFlush(post);
        return postMapper.mapToDetailsDto(post);
    }

    private void attachMedia(PostEntity post, List<UUID> mediaIds) {
        for (int position = 0; position < mediaIds.size(); position++) {
            post.getMedia().add(
                    postMediaMapper.mapToEntity(
                            post,
                            mediaIds.get(position),
                            position
                    )
            );
        }
    }
}