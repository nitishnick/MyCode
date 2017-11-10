package com.sapient.data;

import java.util.List;

public class TradeDataProperties {

	private List<TradeData> tradeDataList;
	private int quantity;
	private int avgLimitPrice;
	private String orderInstructionDetails;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAvgLimitPrice() {
		return avgLimitPrice;
	}

	public void setAvgLimitPrice(int avgLimitPrice) {
		this.avgLimitPrice = avgLimitPrice;
	}

	public String getOrderInstructionDetails() {
		return orderInstructionDetails;
	}

	public void setOrderInstructionDetails(String orderInstructionDetails) {
		this.orderInstructionDetails = orderInstructionDetails;
	}

	public List<TradeData> getTradeDataList() {
		return tradeDataList;
	}

	public void setTradeDataList(List<TradeData> tradeDataList) {
		this.tradeDataList = tradeDataList;
	}

}
