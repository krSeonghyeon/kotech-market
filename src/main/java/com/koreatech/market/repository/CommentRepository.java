package com.koreatech.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreatech.market.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
