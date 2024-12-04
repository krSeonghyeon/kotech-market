package com.koreatech.market.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.koreatech.market.controller.dto.request.ProductCreateRequest;
import com.koreatech.market.controller.dto.response.ProductInfoResponse;
import com.koreatech.market.controller.dto.response.ProductSimpleInfoResponse;
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

    public ProductInfoResponse getProductInfo(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new NotFoundException("찾을 수 없는 상품입니다."));
        return ProductInfoResponse.from(product);
    }

    public List<ProductSimpleInfoResponse> getProductsSimpleInfo(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> productsPage = productRepository.findAll(pageRequest);

        return productsPage.stream()
            .map(ProductSimpleInfoResponse::from)
            .toList();
    }
}
