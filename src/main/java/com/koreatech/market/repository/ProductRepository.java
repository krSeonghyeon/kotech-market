package com.koreatech.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreatech.market.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}