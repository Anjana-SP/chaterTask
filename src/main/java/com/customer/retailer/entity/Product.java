package com.customer.retailer.entity;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Product entity mapped to a database table. This class stores
 * information about a product transaction, including product type, amount,
 * transaction date, and associated customer ID.
 * 
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue
	private int productId;
	private String productType;
	private double amount;
	private Timestamp transactionDate;

	@Column(name = "customer_id")
	private long custId;

}
