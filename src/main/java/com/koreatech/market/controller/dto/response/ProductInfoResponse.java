package com.koreatech.market.controller.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.koreatech.market.domain.Product;

public record ProductInfoResponse(
    Long productId,
    String sellerNickname,
    String categoryName,
    String title,
    String content,
    Long price,
    String image,
    BigDecimal latitude,
    BigDecimal longitude,
    String status,
    LocalDateTime createdAt
) {
    public static ProductInfoResponse from(Product product) {
        return new ProductInfoResponse(
            product.getId(),
            product.getSeller().getNickname(),
            product.getCategory().getName(),
            product.getTitle(),
            product.getContent(),
            product.getPrice(),
            product.getImage(),
            product.getLatitude(),
            product.getLongitude(),
            product.getStatus().toString(),
            product.getCreatedAt()
        );
    }
}
