package com.koreatech.market.controller.dto.response;

import com.koreatech.market.domain.Member;

public record MemberJoinResponse(
    Long id,
    String email,
    String nickname
) {

    public static MemberJoinResponse from(Member member) {
        return new MemberJoinResponse(
            member.getId(),
            member.getEmail(),
            member.getNickname()
        );
    }
}
