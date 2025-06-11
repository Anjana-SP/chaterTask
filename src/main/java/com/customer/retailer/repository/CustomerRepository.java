package com.customer.retailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.customer.retailer.entity.Customer;


/**
 * Repository interface for performing CRUD operations and custom queries
 * on {@link Customer} entities.
 * 
 * This interface extends {@link JpaRepository}, providing built-in methods
 * for saving, deleting, and finding customers. It also includes a custom
 * query method to retrieve a customer by their ID.
 * 
 * Custom Methods:
 * <ul>
 *   <li>{@code findCustomerById(Long customerId)} - Retrieves a customer entity based on the provided customer ID using a JPQL query.</li>
 * </ul>
 * 
 * 
 */


public interface CustomerRepository extends JpaRepository<Customer, Long>  {

	@Query("select c from Customer c where c.customerId=?1")
	public Customer findCustomerById(Long customerId);

	
}
