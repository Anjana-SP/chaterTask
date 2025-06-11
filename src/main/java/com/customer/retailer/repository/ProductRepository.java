package com.customer.retailer.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.customer.retailer.entity.Product;

/**
 * Repository interface for performing CRUD operations and custom queries on
 * {@link Product} entities.
 * 
 * This interface extends {@link JpaRepository}, providing built-in methods for
 * saving, deleting, and finding products. It also includes custom query methods
 * to retrieve product details by customer ID, filter by transaction date range,
 * and fetch a product by its ID.
 * 
 * Custom Methods:
 * <ul>
 * <li>{@code findAllByCustomerId(long cust_id, Timestamp from, Timestamp to)} -
 * Retrieves a list of products for a customer within a specified date
 * range.</li>
 * </ul>
 * 
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p WHERE p.custId = ?1 AND p.transactionDate BETWEEN ?2 AND ?3")
	List<Product> findAllByCustomerId(long custId, Timestamp from, Timestamp to);

}
