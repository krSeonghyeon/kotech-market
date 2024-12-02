package com.koreatech.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreatech.market.controller.dto.request.ProductCreateRequest;
import com.koreatech.market.domain.Category;
import com.koreatech.market.domain.Member;
import com.koreatech.market.domain.Product;
import com.koreatech.market.domain.ProductStatus;
import com.koreatech.market.exception.NotFoundException;
import com.koreatech.market.repository.CategoryRepository;
import com.koreatech.market.repository.MemberRepository;
import com.koreatech.market.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Transactional
    public void createProduct(Long memberId, ProductCreateRequest request) {
        Member seller = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("찾을 수 없는 회원입니다."));

        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new NotFoundException("찾을 수 없는 카테고리입니다."));

        String imageUrl = null;
        if (request.image() != null) {
            imageUrl = fileService.saveImage(request.image());
        }

        Product createProduct = Product.builder()
            .seller(seller)
            .category(category)
            .title(request.title())
            .content(request.content())
            .price(request.price())
            .image(imageUrl)
            .latitude(request.latitude())
            .longitude(request.longitude())
            .status(ProductStatus.판매중)
            .build();

        productRepository.save(createProduct);
    }
}
