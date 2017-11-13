package com.sapient.data;

public class TradeData {
	private long OrderId;
	private String SecurityId;
	private String TransactionCode;
	private String symbol;
	private int quantity;
	private double limitPrice;
	private String orderInstructions;
	private int sumQuantity;
	private double avgLimitPrice;
	private String orderInstructionDetails; // joined details
	private String insertTime;

	public double getAvgLimitPrice() {
		return avgLimitPrice;
	}

	public void setAvgLimitPrice(Double avgLimitPrice) {
		this.avgLimitPrice = avgLimitPrice;
	}

	public String getOrderInstructionDetails() {
		return orderInstructionDetails;
	}

	public void setOrderInstructionDetails(String orderInstructionDetails) {
		this.orderInstructionDetails = orderInstructionDetails;
	}

	public long getOrderId() {
		return OrderId;
	}

	public void setOrderId(long orderId) {
		OrderId = orderId;
	}

	public String getSecurityId() {
		return SecurityId;
	}

	public void setSecurityId(String securityId) {
		SecurityId = securityId;
	}

	public String getTransactionCode() {
		return TransactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		TransactionCode = transactionCode;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSumQuantity() {
		return sumQuantity;
	}

	public void setSumQuantity(int sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	public String getOrderInstructions() {
		return orderInstructions;
	}

	public void setOrderInstructions(String orderInstructions) {
		this.orderInstructions = orderInstructions;
	}

	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}

	public String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}

}
