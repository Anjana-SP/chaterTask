package com.customer.retailer.controller;

import com.customer.retailer.entity.Customer;
import com.customer.retailer.exception.CustomerNotFoundException;
import com.customer.retailer.model.Rewards;
import com.customer.retailer.repository.CustomerRepository;
import com.customer.retailer.serviceimpl.CustomerRewardsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRewardsControllerTest {

	@Mock
	private CustomerRewardsServiceImpl service;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerRewardsController controller;

	private final int CUSTOMER_ID_INT = 123;
	private final long CUSTOMER_ID_LONG = 123L;

	private Rewards testRewardsObj;
	private Customer testCustomerObj;

	@BeforeEach
	void setUp() {
		// prepare a dummy Rewards object
		testRewardsObj = new Rewards();
		testRewardsObj.setCustomerId(CUSTOMER_ID_LONG);
		testRewardsObj.setTotalRewards(250L);
		testRewardsObj.setLastMonthRewardPoints(100L);// setMonth1Rewards(100L);
		testRewardsObj.setLastSecondMonthRewardPoints(75L);
		testRewardsObj.setLastThirdMonthRewardPoints(75L);

		// prepare a dummy Customer
		testCustomerObj = new Customer();
		testCustomerObj.setCustomerId(CUSTOMER_ID_LONG);
		testCustomerObj.setCustName("Jane Doe");
		testCustomerObj.setCustEmail("janeDoe@yahoo.com");
		testCustomerObj.setPhoneNumber(922135548);
	}

	@Test
	void getCurrentMonthPurchaseReward_returnsServiceValue() {
		// Arrange
		long expected = 42L;
		when(service.calculateCurrentMonthPurchaseReward(CUSTOMER_ID_INT)).thenReturn(expected);

		// Act
		long actual = controller.getCurrentMonthPurchaseReward(CUSTOMER_ID_INT);

		// Assert
		assertEquals(expected, actual, "Controller should return the value provided by the service");
		verify(service, times(1)).calculateCurrentMonthPurchaseReward(CUSTOMER_ID_INT);
	}

	@Test
	void getRewardsByCustomerId_whenCustomerExists_returnsOkWithRewards() {
		// Arrange
		when(customerRepository.findCustomerById(CUSTOMER_ID_LONG)).thenReturn(testCustomerObj);
		when(service.getRewardsByCustomerId(CUSTOMER_ID_LONG)).thenReturn(testRewardsObj);

		// Act
		ResponseEntity<Rewards> response = controller.getRewardsByCustomerId(CUSTOMER_ID_LONG);

		// Assert
		assertNotNull(response, "ResponseEntity should not be null");
		assertEquals(HttpStatus.OK, response.getStatusCode(), "Status should be 200 OK");
		assertSame(testRewardsObj, response.getBody(),
				"Body should be the same Rewards object returned by the service");

		verify(customerRepository, times(1)).findCustomerById(CUSTOMER_ID_LONG);
		verify(service, times(1)).getRewardsByCustomerId(CUSTOMER_ID_LONG);
	}

	@Test
	void getRewardsByCustomerId_whenCustomerNotFound_throwsException() {
		// Arrange
		when(customerRepository.findCustomerById(CUSTOMER_ID_LONG)).thenReturn(null);

		// Act & Assert
		CustomerNotFoundException ex = assertThrows(CustomerNotFoundException.class,
				() -> controller.getRewardsByCustomerId(CUSTOMER_ID_LONG),
				"Should throw CustomerNotFoundException when repository returns null");

		assertTrue(ex.getMessage().contains("Invalid  customer Id"),
				"Exception message should mention invalid customer Id");

		verify(customerRepository, times(1)).findCustomerById(CUSTOMER_ID_LONG);
		verify(service, never()).getRewardsByCustomerId(anyLong());
	}
}
