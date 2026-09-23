package com.cohkomapa.imageboard.board.entity.media;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PostMediaId implements Serializable {

    @Column(name = "post_id")
    private UUID postId;

    @Column(name = "media_id")
    private UUID mediaId;
}