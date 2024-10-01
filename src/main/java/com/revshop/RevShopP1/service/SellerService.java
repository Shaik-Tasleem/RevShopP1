package com.revshop.RevShopP1.service;

import java.util.Optional;

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
public void deleteSeller(Long sellerId) {
    sellerRepo.deleteById(sellerId);
}

public Seller updateSeller(Long sellerId, Seller updatedSeller) {
    Optional<Seller> existingSeller = sellerRepo.findById(sellerId);
    
    if (existingSeller.isPresent()) {
        Seller seller = existingSeller.get();
        seller.setFirstName(updatedSeller.getFirstName());
        seller.setLastName(updatedSeller.getLastName());
        seller.setEmail(updatedSeller.getEmail());
        seller.setMobileNumber(updatedSeller.getMobileNumber());
        seller.setPassword(updatedSeller.getPassword());
        seller.setBussinessName(updatedSeller.getBussinessName());
        seller.setStreet(updatedSeller.getStreet());
        seller.setCity(updatedSeller.getCity());
        seller.setPostalCode(updatedSeller.getPostalCode());
        seller.setState(updatedSeller.getState());
        seller.setCountry(updatedSeller.getCountry());
        
        return sellerRepo.save(seller);
    }
    
    return null; 
}
}

