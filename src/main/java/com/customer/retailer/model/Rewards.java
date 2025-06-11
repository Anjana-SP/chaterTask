package com.customer.retailer.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the reward points earned by a customer over the last three months.
 * This model is typically used to calculate and display reward summaries
 * based on customer transactions.
 * 
 * Fields include reward points for the last month, second last month,
 * third last month, and the total reward points.
 * 
 * This class is a simple POJO (Plain Old Java Object) with getters and setters using lombok.
 * 
 * Example usage:
 * <pre>
 *     Rewards rewards = new Rewards();
 *     rewards.setCustomerId(101);
 *     rewards.setLastMonthRewardPoints(120);
 *     rewards.setTotalRewards(300);
 * </pre>
 * 
 */
@Getter
@Setter
public class Rewards {

	private long customerId;
	private long lastMonthRewardPoints;
    private long lastSecondMonthRewardPoints;
    private long lastThirdMonthRewardPoints;
    private long totalRewards;

    
}
