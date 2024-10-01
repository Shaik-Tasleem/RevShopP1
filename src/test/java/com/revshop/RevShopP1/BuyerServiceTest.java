package com.revshop.RevShopP1;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.repository.BuyerRepository;
import com.revshop.RevShopP1.service.BuyerService;

class BuyerServiceTest {

    @Mock
    private BuyerRepository buyerRepo;

    @InjectMocks
    private BuyerService buyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertBuyer() throws NoSuchAlgorithmException {
        Buyer buyer = new Buyer();
        buyer.setFirstName("John");
        buyer.setLastName("Doe");

        buyerService.insertBuyer(buyer);

        verify(buyerRepo, times(1)).save(buyer);
    }

    @Test
    void testDeleteBuyer() {
        Long buyerId = 1L;

        buyerService.deleteBuyer(buyerId);

        verify(buyerRepo, times(1)).deleteById(buyerId);
    }

    @Test
    void testUpdateBuyer_ExistingBuyer() {
        Long buyerId = 1L;
        Buyer existingBuyer = new Buyer();
        existingBuyer.setBuyerId(buyerId);
        existingBuyer.setFirstName("John");
        existingBuyer.setLastName("Doe");

        Buyer updatedBuyer = new Buyer();
        updatedBuyer.setFirstName("Jane");
        updatedBuyer.setLastName("Smith");

        when(buyerRepo.findById(buyerId)).thenReturn(Optional.of(existingBuyer));
        when(buyerRepo.save(any(Buyer.class))).thenReturn(existingBuyer);

        Buyer result = buyerService.updateBuyer(buyerId, updatedBuyer);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(buyerRepo, times(1)).findById(buyerId);
        verify(buyerRepo, times(1)).save(existingBuyer);
    }

    @Test
    void testUpdateBuyer_NonExistingBuyer() {
        Long buyerId = 1L;
        Buyer updatedBuyer = new Buyer();

        when(buyerRepo.findById(buyerId)).thenReturn(Optional.empty());

        Buyer result = buyerService.updateBuyer(buyerId, updatedBuyer);

        assertNull(result);
        verify(buyerRepo, times(1)).findById(buyerId);
        verify(buyerRepo, never()).save(any(Buyer.class));
    }
}