package com.koreatech.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koreatech.market.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
