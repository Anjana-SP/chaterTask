package com.customer.retailer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.customer.retailer.entity.Customer;
import com.customer.retailer.exception.CustomerNotFoundException;
import com.customer.retailer.model.Rewards;
import com.customer.retailer.repository.CustomerRepository;
import com.customer.retailer.serviceimpl.CustomerRewardsServiceImpl;

/**
 * REST controller for managing customer rewards. Provides endpoints for adding,
 * updating, retrieving, and deleting customers and products, as well as
 * calculating reward points.
 */

@RestController
@RequestMapping("/charter")
public class CustomerRewardsController {

	@Autowired
	CustomerRewardsServiceImpl service;

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * Retrieves the current month's reward points for a customer.
	 *
	 * @param customerId the ID of the customer
	 * @return the current month's reward points
	 */
	
	@GetMapping("/getCurrentMonthRewardPoints/{customerId}")
	public Long getCurrentMonthPurchaseReward(@PathVariable int customerId) {
		return service.calculateCurrentMonthPurchaseReward(customerId);
	}

	/**
	 * Retrieves the total reward points for a customer for the period of three
	 * months.
	 *
	 * @param customerId the ID of the customer
	 * @return a ResponseEntity containing the customer's rewards and HTTP status
	 *         code
	 */
	@GetMapping(value = "/totalRewardPoints/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rewards> getRewardsByCustomerId(@PathVariable("customerId") Long customerId) {
		Customer customer = customerRepository.findCustomerById(customerId);
		if (customer == null) {
			throw new CustomerNotFoundException("Invalid  customer Id ");
		}
		Rewards customerRewards = service.getRewardsByCustomerId(customerId);
		return new ResponseEntity<>(customerRewards, HttpStatus.OK);
	}

}
