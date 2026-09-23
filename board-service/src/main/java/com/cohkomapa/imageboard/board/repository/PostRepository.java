package com.cohkomapa.imageboard.board.repository;

import com.cohkomapa.imageboard.board.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID>, JpaSpecificationExecutor<PostEntity> {
}