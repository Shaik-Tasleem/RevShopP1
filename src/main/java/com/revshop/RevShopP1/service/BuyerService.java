package com.revshop.RevShopP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.repository.BuyerRepository;

@Service
public class BuyerService {
	@Autowired
	private BuyerRepository buyerRepo;
	public void insertBuyer(Buyer buyer) {
		buyerRepo.save(buyer);
	}

}
