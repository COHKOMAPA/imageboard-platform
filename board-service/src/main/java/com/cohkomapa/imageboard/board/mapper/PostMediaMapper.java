package com.cohkomapa.imageboard.board.mapper;

import com.cohkomapa.imageboard.board.entity.media.PostMediaEntity;
import com.cohkomapa.imageboard.board.entity.media.PostMediaId;
import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PostMediaMapper {

    default PostMediaEntity mapToEntity(
            PostEntity post,
            UUID mediaId,
            int position
    ) {
        PostMediaEntity postMedia = new PostMediaEntity();
        postMedia.setId(new PostMediaId(post.getId(), mediaId));
        postMedia.setPost(post);
        postMedia.setPosition(position);
        return postMedia;
    }
}