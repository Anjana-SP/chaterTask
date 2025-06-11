package com.customer.retailer.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a Customer entity mapped to the "customer" table in the database.
 * This class is used for storing and retrieving customer-related information.
 * 
 * Fields include customer ID, name, email, and phone number.
 * 
 */

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

	@Id
    @Column(name = "CUSTOMER_ID")
	@GeneratedValue
	private long customerId;
	private String custName;
	private String custEmail;
	private long phoneNumber;
	
	
}
