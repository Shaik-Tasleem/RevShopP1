package com.revshop.RevShopP1;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.revshop.RevShopP1.controller.SellerController;
import com.revshop.RevShopP1.model.Seller;
import com.revshop.RevShopP1.service.EmailService;
import com.revshop.RevShopP1.service.SellerService;

@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @MockBean
    private EmailService emailService;

    private Seller testSeller;

    @BeforeEach
    void setUp() {
        testSeller = new Seller();
        testSeller.setFirstName("Jane");
        testSeller.setLastName("Doe");
        testSeller.setEmail("jane.doe@example.com");
    }

    @Test
    public void testRegistrationForm() throws Exception {
        mockMvc.perform(get("/ecom/sellerRegistration"))
               .andExpect(status().isOk())
               .andExpect(view().name("SellerRegistration"))
               .andExpect(model().attributeExists("seller"));
    }

    @Test
    public void testRegistration() throws Exception {
        mockMvc.perform(post("/ecom/sellerRegistration")
               .flashAttr("seller", testSeller))
               .andExpect(status().isOk())
               .andExpect(view().name("SellerRegistration"));

        verify(sellerService, times(1)).insertSeller(any(Seller.class));
    }

    @Test
    public void testSendVerificationEmail_Success() throws Exception {
        when(emailService.generateOtp()).thenReturn("123456");
        when(emailService.sendEmail(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/ecom/api/send-verificationseller")
               .param("email", "jane.doe@example.com")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(content().string("OTP sent successfully."));
    }

    @Test
    public void testSendVerificationEmail_Failure() throws Exception {
        when(emailService.generateOtp()).thenReturn("123456");
        when(emailService.sendEmail(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/ecom/api/send-verificationseller")
               .param("email", "jane.doe@example.com")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("Failed to send OTP."));
    }

    @Test
    public void testVerifyOtp_Success() throws Exception {
        when(emailService.verifyOtp(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/ecom/api/verify-codeseller")
               .param("email", "jane.doe@example.com")
               .param("code", "123456")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(content().string("OTP verified successfully."));
    }

    @Test
    public void testVerifyOtp_Failure() throws Exception {
        when(emailService.verifyOtp(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/ecom/api/verify-codeseller")
               .param("email", "jane.doe@example.com")
               .param("code", "123456")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Invalid OTP."));
    }
}