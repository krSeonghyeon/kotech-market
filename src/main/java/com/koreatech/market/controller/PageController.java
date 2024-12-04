package com.koreatech.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.koreatech.market.controller.dto.response.ProductInfoResponse;

@Controller
public class PageController {

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
}
