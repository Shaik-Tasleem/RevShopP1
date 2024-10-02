package com.revshop.RevShopP1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revshop.RevShopP1.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
 

    // Custom query to find a category by its name
    Category findByCategoryName(String categoryName);
}
