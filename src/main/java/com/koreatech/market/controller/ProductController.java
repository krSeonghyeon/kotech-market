package com.koreatech.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.koreatech.market.controller.dto.request.ProductCreateRequest;
import com.koreatech.market.controller.dto.response.ProductInfoResponse;
import com.koreatech.market.domain.Product;
import com.koreatech.market.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(
        @SessionAttribute(name = "memberId") Long memberId,
        @ModelAttribute @Valid ProductCreateRequest request
    ) {
        productService.createProduct(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInfoResponse> getProductInfo(@PathVariable Long id) {
        ProductInfoResponse response = productService.getProductInfo(id);
        return ResponseEntity.ok(response);
    }
}
