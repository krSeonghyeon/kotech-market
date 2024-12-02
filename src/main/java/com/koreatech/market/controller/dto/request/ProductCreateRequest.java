package com.koreatech.market.controller.dto.request;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductCreateRequest(
    Long categoryId,

    @NotBlank(message = "상품명은 비어있을 수 없습니다")
    @Size(max = 255, message = "상품명은 최대 255자까지 가능합니다")
    String title,

    @NotBlank(message = "상품설명은 비어있을 수 없습니다")
    @Size(max = 255, message = "상품설명은 최대 255자까지 가능합니다")
    String content,

    @NotNull(message = "가격은 비어있을 수 없습니다")
    @Min(value = 0, message = "가격은 0이상이어야 합니다")
    Long price,

    MultipartFile image,

    @NotNull(message = "위도는 비어있을 수 없습니다")
    BigDecimal latitude,

    @NotNull(message = "경도는 비어있을 수 없습니다")
    BigDecimal longitude
) {
}
