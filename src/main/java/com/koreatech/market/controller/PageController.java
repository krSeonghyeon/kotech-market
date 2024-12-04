package com.koreatech.market.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.koreatech.market.controller.dto.response.ProductInfoResponse;
import com.koreatech.market.controller.dto.response.ProductSimpleInfoResponse;
import com.koreatech.market.domain.Category;
import com.koreatech.market.repository.CategoryRepository;
import com.koreatech.market.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/product-detail/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        String apiUrl = "http://localhost:8080/products/" + id;
        ResponseEntity<ProductInfoResponse> response = restTemplate.getForEntity(apiUrl, ProductInfoResponse.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("상품 정보를 가져오는 데 실패했습니다.");
        }

        ProductInfoResponse productInfo = response.getBody();
        model.addAttribute("product", productInfo);

        return "product-detail";
    }

    @GetMapping("/products/categories/{categoryId}")
    public String getCategoryProducts(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size,
        @RequestParam(required = false) Long minPrice,
        @RequestParam(required = false) Long maxPrice,
        Model model
    ) {
        Page<ProductSimpleInfoResponse> products = productService.getFilteredProducts(
            categoryId, null, minPrice, maxPrice, page, size
        );

        String categoryName = categoryRepository.findById(categoryId)
            .map(Category::getName)
            .orElse(null);

        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "category";
    }

}
