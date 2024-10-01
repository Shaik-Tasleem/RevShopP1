package com.revshop.RevShopP1;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.repository.SellerRepository;
import com.revshop.RevShopP1.service.SellerService;

public class SellerServiceTest {

    @Mock
    private SellerRepository sellerRepo;

    @InjectMocks
    private SellerService sellerService;

    private Seller testSeller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testSeller = new Seller();
        testSeller.setSellerId(1L);
        testSeller.setFirstName("John");
        testSeller.setLastName("Doe");
        testSeller.setEmail("john.doe@example.com");
        testSeller.setMobileNumber("1234567890");
        testSeller.setPassword("password");
        testSeller.setBussinessName("John's Shop");
        testSeller.setStreet("123 Main St");
        testSeller.setCity("Anytown");
        testSeller.setPostalCode(Integer.parseInt("12345"));
        testSeller.setState("State");
        testSeller.setCountry("Country");
    }

    @Test
    public void testInsertSeller() {
        sellerService.insertSeller(testSeller);
        verify(sellerRepo, times(1)).save(testSeller);
    }

    @Test
    public void testDeleteSeller() {
        Long sellerId = 1L;
        sellerService.deleteSeller(sellerId);
        verify(sellerRepo, times(1)).deleteById(sellerId);
    }

    @Test
    public void testUpdateSeller_Success() {
        Long sellerId = 1L;
        Seller updatedSeller = new Seller();
        updatedSeller.setFirstName("Jane");
        updatedSeller.setLastName("Doe");
        updatedSeller.setEmail("jane.doe@example.com");
        updatedSeller.setMobileNumber("0987654321");
        updatedSeller.setPassword("newpassword");
        updatedSeller.setBussinessName("Jane's Shop");
        updatedSeller.setStreet("456 Main St");
        updatedSeller.setCity("Othertown");
        updatedSeller.setPostalCode(Integer.parseInt("54321"));
        updatedSeller.setState("NewState");
        updatedSeller.setCountry("NewCountry");

        when(sellerRepo.findById(sellerId)).thenReturn(Optional.of(testSeller));
        when(sellerRepo.save(any(Seller.class))).thenReturn(updatedSeller);

        Seller result = sellerService.updateSeller(sellerId, updatedSeller);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("jane.doe@example.com", result.getEmail());
        assertEquals("0987654321", result.getMobileNumber());
        assertEquals("newpassword", result.getPassword());
        assertEquals("Jane's Shop", result.getBussinessName());
        assertEquals("456 Main St", result.getStreet());
        assertEquals("Othertown", result.getCity());
        assertEquals("54321", result.getPostalCode());
        assertEquals("NewState", result.getState());
        assertEquals("NewCountry", result.getCountry());

        verify(sellerRepo, times(1)).findById(sellerId);
        verify(sellerRepo, times(1)).save(any(Seller.class));
    }

    @Test
    public void testUpdateSeller_NotFound() {
        Long sellerId = 1L;
        Seller updatedSeller = new Seller();

        when(sellerRepo.findById(sellerId)).thenReturn(Optional.empty());

        Seller result = sellerService.updateSeller(sellerId, updatedSeller);

        assertNull(result);
        verify(sellerRepo, times(1)).findById(sellerId);
        verify(sellerRepo, times(0)).save(any(Seller.class));
    }
}
