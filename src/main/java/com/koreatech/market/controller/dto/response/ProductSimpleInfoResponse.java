package com.koreatech.market.controller.dto.response;

import java.time.LocalDateTime;

import com.koreatech.market.domain.Product;

public record ProductSimpleInfoResponse(
    Long productId,
    String title,
    Long price,
    String image,
    LocalDateTime createdAt
) {
    public static ProductSimpleInfoResponse from(Product product) {
        return new ProductSimpleInfoResponse(
            product.getId(),
            product.getTitle(),
            product.getPrice(),
            product.getImage(),
            product.getCreatedAt()
        );
    }
}
