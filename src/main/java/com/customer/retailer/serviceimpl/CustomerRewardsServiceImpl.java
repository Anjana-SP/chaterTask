package com.customer.retailer.serviceimpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.customer.retailer.entity.Customer;
import com.customer.retailer.entity.Product;
import com.customer.retailer.model.Rewards;
import com.customer.retailer.repository.CustomerRepository;
import com.customer.retailer.repository.ProductRepository;

/**
 * Service implementation for managing customer rewards. This class provides
 * calculating rewards for customers based on their transactions.
 */
@Service
public class CustomerRewardsServiceImpl implements CustomerRetailerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ProductRepository productRepository;

	@Value("${app.days-in-months}")
	private int months;
	@Value("${app.firstRewardPrice}")
	private int firstRewardLimit;
	@Value("${app.seconRewardPrice}")
	private int secondRewardLimit;
	@Value("${app.second}")
	private static int second;
	@Value("${app.third}")
	private static int third;

	/**
	 * Calculates the rewards for a customer based on their transactions in the last
	 * three months.
	 * 
	 * @param customerId the ID of the customer
	 * @return the rewards for the customer
	 */
	public Rewards getRewardsByCustomerId(Long customerId) {

		Timestamp firstMonthTimestamp = getDate(getMonths());
		Timestamp secondMonthTimestamp = getDate(second * getMonths());
		Timestamp thirdMonthTimestamp = getDate(third * getMonths());

		List<Product> lastMonthTransactions = productRepository.findAllByCustomerId(customerId, firstMonthTimestamp,
				Timestamp.from(Instant.now()));
		List<Product> lastSecondMonthTransactions = productRepository.findAllByCustomerId(customerId,
				secondMonthTimestamp, firstMonthTimestamp);
		List<Product> lastThirdMonthTransactions = productRepository.findAllByCustomerId(customerId,
				thirdMonthTimestamp, secondMonthTimestamp);

		Long lastMonthRewardPoints = getRewardsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getRewardsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getRewardsPerMonth(lastThirdMonthTransactions);

		Rewards customerRewards = new Rewards();
		customerRewards.setCustomerId(customerId);
		customerRewards.setLastMonthRewardPoints(lastMonthRewardPoints);
		customerRewards.setLastSecondMonthRewardPoints(lastSecondMonthRewardPoints);
		customerRewards.setLastThirdMonthRewardPoints(lastThirdMonthRewardPoints);
		customerRewards
				.setTotalRewards(lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints);

		return customerRewards;

	}

	/**
	 * Calculates the rewards for a list of transactions in a month.
	 * 
	 * @param lastMonthTransactions the list of transactions
	 * @return the total rewards for the transactions
	 */
	private Long getRewardsPerMonth(List<Product> lastMonthTransactions) {

		return lastMonthTransactions.stream().mapToLong(reward -> calculateRewards(reward)).sum();
	}

	/**
	 * Calculates the rewards for a single transaction.
	 * 
	 * @param p the product transaction
	 * @return the rewards for the transaction
	 */
	private Long calculateRewards(Product p) {
	    double amount = p.getAmount();
	    if (amount > getSecondRewardLimit()) {
	        return Math.round((amount - getSecondRewardLimit()) * 2 + (getSecondRewardLimit() - getFirstRewardLimit()));
	    } else if (amount > getFirstRewardLimit()) {
	        return Math.round(amount - getFirstRewardLimit());
	    } else {
	        return 0L;
	    }
	}

	/**
	 * Calculates the rewards for a customer's transactions in the current month.
	 * 
	 * @param customerId the ID of the customer
	 * @return the total rewards for the current month
	 */
	@Override
	public Long calculateCurrentMonthPurchaseReward(int customerId) {

		Timestamp lastMonthTimestamp = getDate(getMonths());
		List<Product> lastMonthTransactions = productRepository.findAllByCustomerId(customerId, lastMonthTimestamp,
				Timestamp.from(Instant.now()));
		return getRewardsPerMonth(lastMonthTransactions);
	}

	/**
	 * Retrieves a customer by their ID.
	 * 
	 * @param id the ID of the customer
	 * @return the customer with the specified ID
	 */
	@Override
	public Customer getCustomerById(Long id) {

		return customerRepository.findCustomerById(id);
	}

	/**
	 * Helper method to get a timestamp for a specified number of days in the past.
	 * 
	 * @param d the number of days
	 * @return the timestamp for the specified number of days in the past
	 */
	 Timestamp getDate(int d) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(d));
	}

	public int getFirstRewardLimit() {
		return firstRewardLimit;
	}

	public void setFirstRewardLimit(int firstRewardLimit) {
		this.firstRewardLimit = firstRewardLimit;
	}

	public int getSecondRewardLimit() {
		return secondRewardLimit;
	}

	public void setSecondRewardLimit(int secondRewardLimit) {
		this.secondRewardLimit = secondRewardLimit;
	}

	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
	}

}
