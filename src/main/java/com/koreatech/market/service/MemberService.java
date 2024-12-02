package com.koreatech.market.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreatech.market.controller.dto.request.MemberJoinRequest;
import com.koreatech.market.controller.dto.response.MemberJoinResponse;
import com.koreatech.market.domain.Member;
import com.koreatech.market.exception.DuplicationException;
import com.koreatech.market.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberJoinResponse join(MemberJoinRequest request) {
        if (memberRepository.existsByEmail(request.email())) {
            throw new DuplicationException("이미 사용 중인 이메일입니다.");
        }
        if (memberRepository.existsByNickname(request.nickname())) {
            throw new DuplicationException("이미 사용 중인 닉네임입니다.");
        }
        if (memberRepository.existsByStudentNumber(request.student_number())) {
            throw new DuplicationException("이미 사용 중인 학번입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Member joinMember = Member.builder()
            .email(request.email())
            .password(encodedPassword)
            .name(request.name())
            .nickname(request.nickname())
            .studentNumber(request.student_number())
            .phoneNumber(request.phone_number())
            .build();

        memberRepository.save(joinMember);

        return MemberJoinResponse.from(joinMember);
    }
}
