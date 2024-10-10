package com.revshop.RevShopP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revshop.RevShopP1.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Additional query methods if needed
}
