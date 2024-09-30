package com.revshop.RevShopP1.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.repository.BuyerRepository;
import com.revshop.RevShopP1.utils.PasswordUtils;

@Service
public class BuyerService {
	@Autowired
	private BuyerRepository buyerRepo;
	@Autowired
	private PasswordUtils pwd_obj;
	public void insertBuyer(Buyer buyer) throws NoSuchAlgorithmException {
		buyer.setPassword(pwd_obj.hashPassword(buyer.getPassword()));
		buyerRepo.save(buyer);
	}
	public Buyer getBuyerDetailsByEmail(String email) {
		return buyerRepo.findByEmail(email);
	}
	public Buyer getBuyerDetailsByMobileNumber(String mobileNumber) {
		return buyerRepo.findByMobileNumber(mobileNumber);
	}

}
