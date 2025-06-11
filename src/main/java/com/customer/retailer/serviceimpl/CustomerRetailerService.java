package com.customer.retailer.serviceimpl;

import com.customer.retailer.entity.Customer;
import com.customer.retailer.model.Rewards;

public interface CustomerRetailerService {

	public Customer getCustomerById(Long id);

	public Rewards getRewardsByCustomerId(Long customerId);

	public Long calculateCurrentMonthPurchaseReward(int customerId);

}
