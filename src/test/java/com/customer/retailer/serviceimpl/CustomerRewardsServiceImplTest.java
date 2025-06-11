
package com.customer.retailer.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import com.customer.retailer.entity.Customer;
import com.customer.retailer.entity.Product;
import com.customer.retailer.model.Rewards;
import com.customer.retailer.repository.CustomerRepository;
import com.customer.retailer.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomerRewardsServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CustomerRewardsServiceImpl customerRewardsService;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        setPrivateField(customerRewardsService, "months", 30);
        setPrivateField(customerRewardsService, "firstRewardLimit", 50);
        setPrivateField(customerRewardsService, "secondRewardLimit", 100);

        // Static fields
        var fieldSecond = CustomerRewardsServiceImpl.class.getDeclaredField("second");
        fieldSecond.setAccessible(true);
        fieldSecond.set(null, 2);

        var fieldThird = CustomerRewardsServiceImpl.class.getDeclaredField("third");
        fieldThird.setAccessible(true);
        fieldThird.set(null, 3);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        var field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }


    @Test
    void testGetRewardsByCustomerId() {
        Long customerId = 1L;
        Product p1 = new Product(1, "Electronics", 120.0, Timestamp.from(Instant.now()), customerId);
        Product p2 = new Product(2, "Grocery", 80.0, Timestamp.from(Instant.now()), customerId);

        when(productRepository.findAllByCustomerId(eq(customerId), any(), any()))
                .thenReturn(Arrays.asList(p1, p2));

        Rewards rewards = customerRewardsService.getRewardsByCustomerId(customerId);

        assertNotNull(rewards);
        assertEquals(customerId, rewards.getCustomerId());
        assertTrue(rewards.getTotalRewards() > 0);
    }

    @Test
    void testCalculateCurrentMonthPurchaseReward() {
        int customerId = 1;
        Product p1 = new Product(1, "Electronics", 120.0, Timestamp.from(Instant.now()), customerId);

        when(productRepository.findAllByCustomerId(eq((long) customerId), any(), any()))
                .thenReturn(Collections.singletonList(p1));

        Long reward = customerRewardsService.calculateCurrentMonthPurchaseReward(customerId);

        assertNotNull(reward);
        assertTrue(reward > 0);
    }

    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustName("John Doe");

        when(customerRepository.findCustomerById(customerId)).thenReturn(customer);

        Customer result = customerRewardsService.getCustomerById(customerId);

        assertNotNull(result);
        assertEquals("John Doe", result.getCustName());
    }

    @Test
    void testGetRewardsByCustomerId_NoTransactions() {
        Long customerId = 2L;

        when(productRepository.findAllByCustomerId(eq(customerId), any(), any()))
                .thenReturn(Collections.emptyList());

        Rewards rewards = customerRewardsService.getRewardsByCustomerId(customerId);

        assertNotNull(rewards);
        assertEquals(0L, rewards.getTotalRewards());
        assertEquals(0L, rewards.getLastMonthRewardPoints());
        assertEquals(0L, rewards.getLastSecondMonthRewardPoints());
        assertEquals(0L, rewards.getLastThirdMonthRewardPoints());
    }

    @Test
    void testGetRewardsByCustomerId_TransactionsBelowThreshold() {
        Long customerId = 3L;
        Product p1 = new Product(1, "Books", 30.0, Timestamp.from(Instant.now()), customerId);

        when(productRepository.findAllByCustomerId(eq(customerId), any(), any()))
                .thenReturn(Collections.singletonList(p1));

        Rewards rewards = customerRewardsService.getRewardsByCustomerId(customerId);

        assertNotNull(rewards);
        assertEquals(0L, rewards.getTotalRewards());
    }

    @Test
    void testCalculateRewards_AtThresholds() {
        Long customerId = 4L;
        Product p1 = new Product(1, "Stationery", 100.0, Timestamp.from(Instant.now()), customerId);
        Product p2 = new Product(2, "Toys", 50.0, Timestamp.from(Instant.now()), customerId);

        when(productRepository.findAllByCustomerId(eq(customerId), any(), any()))
                .thenReturn(Arrays.asList(p1, p2));

        Rewards rewards = customerRewardsService.getRewardsByCustomerId(customerId);

        assertNotNull(rewards);
        assertEquals(150L, rewards.getTotalRewards());
    }

    @Test
    void testGetCustomerById_NullCustomer() {
        Long customerId = 5L;

        when(customerRepository.findCustomerById(customerId)).thenReturn(null);

        Customer result = customerRewardsService.getCustomerById(customerId);

        assertNull(result);
    }

    @Test
    void testCalculateCurrentMonthPurchaseReward_NoTransactions() {
        int customerId = 6;

        when(productRepository.findAllByCustomerId(eq((long) customerId), any(), any()))
                .thenReturn(Collections.emptyList());

        Long reward = customerRewardsService.calculateCurrentMonthPurchaseReward(customerId);

        assertEquals(0L, reward);
    }
}
