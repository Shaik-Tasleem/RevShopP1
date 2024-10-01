package com.revshop.RevShopP1.service;

import java.util.Optional;

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
	public void deleteBuyer(Long buyerId) {
        buyerRepo.deleteById(buyerId);
    }

    public Buyer updateBuyer(Long buyerId, Buyer updatedBuyer) {
        Optional<Buyer> existingBuyer = buyerRepo.findById(buyerId);
        
        if (existingBuyer.isPresent()) {
            Buyer buyer = existingBuyer.get();
            buyer.setFirstName(updatedBuyer.getFirstName());  
            buyer.setLastName(updatedBuyer.getLastName());
            buyer.setEmail(updatedBuyer.getEmail());
            buyer.setMobileNumber(updatedBuyer.getMobileNumber());
            buyer.setStreet(updatedBuyer.getStreet());
            buyer.setCity(updatedBuyer.getCity());
            buyer.setPostalCode(updatedBuyer.getPostalCode());  
            buyer.setState(updatedBuyer.getState());  
            buyer.setCountry(updatedBuyer.getCountry());  
            return buyerRepo.save(buyer); 
        }
        
        return null; // Or throw an exception if the buyer doesn't exist
    }


}
