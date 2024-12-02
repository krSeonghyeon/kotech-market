package com.koreatech.market.controller.dto.response;

import com.koreatech.market.domain.Member;

public record MemberInfoResponse(
    String email,
    String name,
    String nickname,
    String student_number,
    String phone_number
) {

    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(
            member.getEmail(),
            member.getName(),
            member.getNickname(),
            member.getStudentNumber(),
            member.getPhoneNumber()
        );
    }
}
