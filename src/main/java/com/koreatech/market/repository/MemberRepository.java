package com.koreatech.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreatech.market.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
