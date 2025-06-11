package com.customer.retailer.exception;

public class CustomerNotFoundException extends RuntimeException {


/**
 * Exception thrown when a requested customer is not found in the system.
 * 
 * This is a custom runtime exception used to indicate that a customer
 * with the specified criteria does not exist in the database or application context.
 * 
 * It extends {@link RuntimeException}, so it is an unchecked exception.
 * 
 * Example usage:
 * <pre>
 *     throw new CustomerNotFoundException("Customer with ID 123 not found.");
 * </pre>
 * 
 */

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
}
