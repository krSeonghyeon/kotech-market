package com.koreatech.market.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.koreatech.market.controller.dto.request.ProductCreateRequest;
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
}
