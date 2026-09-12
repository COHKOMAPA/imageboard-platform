package com.cohkomapa.imageboard.media.repository;

import com.cohkomapa.imageboard.media.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}