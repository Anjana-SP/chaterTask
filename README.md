# chaterTask
Calculating the reward points earned by  customer. 

## Overview
This is a Spring Boot service for managing customer rewards. The service provides methods for adding, updating, and deleting customers and products, as well as calculating rewards for customers based on their transactions.

## Reward System
A retailer offers a rewards program to its customers, awarding points based on each recorded purchase. A customer receives:
- 2 points for every dollar spent over $100 in each transaction
- 1 point for every dollar spent between $50 and $100 in each transaction

For example, a $120 purchase = 2x$20 + 1x$50 = 90 points.

## Features

- Calculate rewards for a customer based on their transactions in the last three months
- Calculate rewards for a customer's transactions in the current month

## Classes and Methods

### CustomerRewardsServiceImpl

- `getRewardsByCustomerId(Long customerId)`: Calculates the rewards for a customer based on their transactions in the last three months.
- `calculateCurrentMonthPurchaseReward(int customerId)`: Calculates the rewards for a customer's transactions in the current month.
- `getRewardsPerMonth(List<Product> lastMonthTransactions)`: Calculates the rewards for a list of transactions in a month.
- `calculateRewards(Product p)`: Calculates the rewards for a single transaction.
- `getDate(int d)`: Helper method to get a timestamp for a specified number of days in the past.

## Usage

- Calculate rewards for a customer based on their transactions in the last three months using `getRewardsByCustomerId(Long customerId)`.
- Calculate rewards for a customer's transactions in the current month using `calculateCurrentMonthPurchaseReward(int customerId)`.

## Database
We have 2 tables in the database. The `Customer` table stores customer details, and the `Product` table stores product details and the products purchased by each customer.

### customer_tbl
| custId | custName | customerEmail   | phoneNumber |
|--------|----------|------------------|--------------|
| 1      | Jane Doe | janeD@gmail.com | 9432938331   |
| 2      | Alex     | alex@yahoo.com  | 8897839293   |

### product_tbl
| productId | productName       | productPrice | custId | transactionDate         |
|-----------|-------------------|--------------|--------|--------------------------|
| 101       | bluetooth Remote  | 120          | 1      | 2025-05-23 05:30:00      |
| 102       | books             | 70           | 1      | 2025-06-10 15:10:04      |
-------------------------------------------------------------------------------------
##Example

In the current month (June 2025):
Customer 1 made a purchase of $70.
Earned 20 reward points (1 point for each dollar between $50 and $100).

In the past three months (April to June 2025):
Two purchases: $120 (May) and $70 (June).
Earned a total of 110 reward points:
90 points from the $120 purchase (50 points for $50–$100, and 2×20 = 40 points for the amount over $100).
20 points from the $70 purchase.

## Testing
This project includes unit tests for all major service methods using JUnit 5 and Mockito.

## Test Coverage Includes:
Reward calculation logic for individual and monthly transactions
Edge cases such as no transactions or boundary reward values
