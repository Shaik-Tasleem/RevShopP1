package com.revshop.RevShopP1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.RevShopP1.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	 List<Category> findByCategoryName(String categoryName);

}
