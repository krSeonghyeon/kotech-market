package com.koreatech.market.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberModifyRequest(
    @NotBlank(message = "닉네임은 비어있을 수 없습니다")
    @Size(max = 50, message = "닉네임은 최대 50자까지 가능합니다")
    String nickname,

    @NotBlank(message = "전화번호는 비어있을 수 없습니다")
    @Size(max = 20, message = "전화번호는 최대 20자까지 가능합니다")
    String phoneNumber
) {
}
