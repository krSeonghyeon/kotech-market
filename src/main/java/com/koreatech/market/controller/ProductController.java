package com.koreatech.market.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.koreatech.market.controller.dto.request.ProductCreateRequest;
import com.koreatech.market.controller.dto.response.ProductInfoResponse;
import com.koreatech.market.controller.dto.response.ProductSimpleInfoResponse;
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

    @GetMapping("/simple-info")
    public ResponseEntity<List<ProductSimpleInfoResponse>> getProductsSimpleInfo(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "4") int size
    ) {
        List<ProductSimpleInfoResponse> responses = productService.getProductsSimpleInfo(page, size);
        return ResponseEntity.ok(responses);
    }

    @GetMapping
    public Page<ProductSimpleInfoResponse> getProducts(
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) Long minPrice,
        @RequestParam(required = false) Long maxPrice,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size
    ) {
        return productService.getFilteredProducts(categoryId, title, minPrice, maxPrice, page, size);
    }
}
