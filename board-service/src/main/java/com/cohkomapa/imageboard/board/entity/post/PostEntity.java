package com.cohkomapa.imageboard.board.entity.post;

import com.cohkomapa.imageboard.board.entity.media.PostMediaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class PostEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(
            mappedBy = "post",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @OrderBy("position asc")
    private List<PostMediaEntity> media = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;
}