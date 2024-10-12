package com.revshop.RevShopP1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Orders;
import com.revshop.RevShopP1.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Method to save an order
    public void saveOrder(Orders order) {
        orderRepository.save(order);
    }
    public List<Orders> getOrdersByBuyerId(Long buyerId) {
        return orderRepository.findByBuyer_BuyerId(buyerId);
    }
	public List<Orders> getOrdersBySellerId(Long sellerId) {
		// TODO Auto-generated method stub
		return orderRepository.findBySeller_SellerId(sellerId);
	}
}