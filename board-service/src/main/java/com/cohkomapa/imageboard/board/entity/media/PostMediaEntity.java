package com.cohkomapa.imageboard.board.entity.media;

import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "post_media")
@Getter
@Setter
@NoArgsConstructor
public class PostMediaEntity {

    @EmbeddedId
    private PostMediaId id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Column(name = "position", nullable = false)
    int position;
}