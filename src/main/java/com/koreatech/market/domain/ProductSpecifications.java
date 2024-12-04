package com.koreatech.market.domain;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> categoryEquals(Long categoryId) {
        return (root, query, criteriaBuilder) ->
            categoryId == null ? null : criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> titleContains(String title) {
        return (root, query, criteriaBuilder) ->
            title == null || title.isEmpty() ? null : criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Product> priceBetween(Long minPrice, Long maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            } else if (minPrice == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else if (maxPrice == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
        };
    }
}
