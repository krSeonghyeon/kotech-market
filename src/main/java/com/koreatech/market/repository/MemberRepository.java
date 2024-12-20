package com.koreatech.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreatech.market.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByStudentNumber(String studentNumber);

    Optional<Member> findByEmail(String email);
}
