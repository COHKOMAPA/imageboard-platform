package com.cohkomapa.imageboard.media.repository;

import com.cohkomapa.imageboard.media.entity.Media;
import com.cohkomapa.imageboard.media.enums.MediaStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {

    @Query("""
            select m.id from Media m
            where m.status = :status
                and m.createdAt < :createdBefore
            order by m.createdAt asc
            """)
    List<UUID> findIdsByStatusAndCreatedBefore(
            MediaStatus status,
            ZonedDateTime createdBefore,
            Pageable pageable
    );
}