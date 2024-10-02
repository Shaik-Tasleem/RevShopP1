package com.revshop.RevShopP1;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.revshop.RevShopP1.controller.BuyerController;
import com.revshop.RevShopP1.model.Buyer;
import com.revshop.RevShopP1.service.BuyerService;
import com.revshop.RevShopP1.service.EmailService;
import com.revshop.RevShopP1.utils.PasswordUtils;

public class BuyerControllerTest {

    @Mock
    private BuyerService buyerService;

    @Mock
    private EmailService emailService;
    
    @Mock
    private PasswordUtils pwd_obj;

    @InjectMocks
    private BuyerController buyerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buyerController).build();
    }

    @Test
    public void testRegistrationForm() throws Exception {
        mockMvc.perform(get("/ecom/buyerRegistration"))
               .andExpect(status().isOk())
               .andExpect(view().name("BuyerRegistration"))
               .andExpect(model().attributeExists("buyer"));
    }

    @Test
    public void testRegistration() throws Exception {
        Buyer buyer = new Buyer();
        mockMvc.perform(post("/ecom/buyerRegistration")
               .flashAttr("buyer", buyer))
               .andExpect(status().isOk())
               .andExpect(view().name("LoginPage"));

        verify(buyerService, times(1)).insertBuyer(buyer);
    }

    @Test
    public void testSendVerificationEmail_Success() throws Exception {
        when(emailService.generateOtp()).thenReturn("123456");
        when(emailService.sendEmail(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/ecom/api/send-verification")
               .param("email", "test@example.com")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(content().string("OTP sent successfully."));

        verify(emailService, times(1)).generateOtp();
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), anyString());
    }

    @Test
    public void testSendVerificationEmail_Failure() throws Exception {
        when(emailService.generateOtp()).thenReturn("123456");
        when(emailService.sendEmail(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/ecom/api/send-verification")
               .param("email", "test@example.com")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("Failed to send OTP."));

        verify(emailService, times(1)).generateOtp();
        verify(emailService, times(1)).sendEmail(eq("test@example.com"), anyString());
    }

    @Test
    public void testVerifyOtp_Success() throws Exception {
        when(emailService.verifyOtp(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/ecom/api/verify-code")
               .param("email", "test@example.com")
               .param("code", "123456")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(content().string("OTP verified successfully."));

        verify(emailService, times(1)).verifyOtp("test@example.com", "123456");
    }

    @Test
    public void testVerifyOtp_Failure() throws Exception {
        when(emailService.verifyOtp(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/ecom/api/verify-code")
               .param("email", "test@example.com")
               .param("code", "123456")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Invalid OTP."));

        verify(emailService, times(1)).verifyOtp("test@example.com", "123456");
    }
    @Test
    public void testBuyerLoginSuccessWithEmail() throws Exception {
        Buyer mockBuyer = new Buyer();
        mockBuyer.setEmail("test@example.com");
        mockBuyer.setPassword("hashedPassword");

        when(buyerService.getBuyerDetailsByEmail(anyString())).thenReturn(mockBuyer);
        when(pwd_obj.hashPassword(anyString())).thenReturn("hashedPassword");

        mockMvc.perform(post("/ecom/buyer/handleLogin")
               .param("email", "test@example.com")
               .param("password", "password123")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(view().name("LoginPage"))
               .andExpect(model().attributeDoesNotExist("errorMessage"));

        verify(buyerService, times(1)).getBuyerDetailsByEmail("test@example.com");
    }
    @Test
    public void testBuyerLoginFailureWithEmailInvalidPassword() throws Exception {
        Buyer mockBuyer = new Buyer();
        mockBuyer.setEmail("test@example.com");
        mockBuyer.setPassword("hashedPassword");

        when(buyerService.getBuyerDetailsByEmail(anyString())).thenReturn(mockBuyer);
        when(pwd_obj.hashPassword(anyString())).thenReturn("wrongHashedPassword");

        mockMvc.perform(post("/ecom/buyer/handleLogin")
               .param("email", "test@example.com")
               .param("password", "wrongPassword")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(view().name("LoginPage"))
               .andExpect(model().attribute("errorMessage", "Invalid Email or Password...\nIf you are a new user Kindly...Register..\nTo access our Services.."));

        verify(buyerService, times(1)).getBuyerDetailsByEmail("test@example.com");
    }
    @Test
    public void testBuyerLoginSuccessWithMobileNumber() throws Exception {
        Buyer mockBuyer = new Buyer();
        mockBuyer.setMobileNumber("1234567890");
        mockBuyer.setPassword("hashedPassword");

        when(buyerService.getBuyerDetailsByMobileNumber(anyString())).thenReturn(mockBuyer);
        when(pwd_obj.hashPassword(anyString())).thenReturn("hashedPassword");

        mockMvc.perform(post("/ecom/buyer/handleLogin")
               .param("mobileNumber", "1234567890")
               .param("password", "password123")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(view().name("LoginPage"))
               .andExpect(model().attributeDoesNotExist("errorMessage"));

        verify(buyerService, times(1)).getBuyerDetailsByMobileNumber("1234567890");
    }
    @Test
    public void testBuyerLoginFailureWithMobileNumberInvalidPassword() throws Exception {
        Buyer mockBuyer = new Buyer();
        mockBuyer.setMobileNumber("1234567890");
        mockBuyer.setPassword("hashedPassword");

        when(buyerService.getBuyerDetailsByMobileNumber(anyString())).thenReturn(mockBuyer);
        when(pwd_obj.hashPassword(anyString())).thenReturn("wrongHashedPassword");

        mockMvc.perform(post("/ecom/buyer/handleLogin")
               .param("mobileNumber", "1234567890")
               .param("password", "wrongPassword")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andExpect(view().name("LoginPage"))
               .andExpect(model().attribute("errorMessage", "Invalid Email or Password...\nIf you are a new user Kindly...Register to access our Services.."));

        verify(buyerService, times(1)).getBuyerDetailsByMobileNumber("1234567890");
    }

}