package com.revshop.RevShopP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.repository.SellerRepository;

@Service
public class SellerService {
@Autowired
private SellerRepository sellerRepo;
public void insertSeller(Seller seller) {
	sellerRepo.save(seller);
}

}
