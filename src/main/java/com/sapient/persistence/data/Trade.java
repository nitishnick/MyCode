package com.sapient.persistence.data;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trade")
public class Trade implements java.io.Serializable {


	private long orderId;


	private String securityId;


	private String transactionCode;

	private String symbol;

	private long quantity;


	private double limitPrice;


	private String orderInstructions;

	private GroupedTradeMapping groupedTradeMapping;
	
	
	public Trade() {
	}
	public Trade(long orderId, String securityId, String transactionCode, String symbol, long quantity,
			double limitPrice, String orderInstructions, GroupedTradeMapping groupedTradeMapping) {
		this.orderId = orderId;
		this.securityId = securityId;
		this.transactionCode = transactionCode;
		this.symbol = symbol;
		this.quantity = quantity;
		this.limitPrice = limitPrice;
		this.orderInstructions = orderInstructions;
		this.groupedTradeMapping = groupedTradeMapping;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id", unique = true, nullable = false)
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "security_id")
	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	@Column(name = "transaction_code", nullable = false, length=50)
	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	@Column(name = "limit_price")
	public double getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(double limitPrice) {
		this.limitPrice = limitPrice;
	}
	
	@Column(name = "order_instructions")
	public String getOrderInstructions() {
		return orderInstructions;
	}

	public void setOrderInstructions(String orderInstructions) {
		this.orderInstructions = orderInstructions;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", nullable = false)
	public GroupedTradeMapping getGroupedTradeMapping() {
		return groupedTradeMapping;
	}

	public void setGroupedTradeMapping(GroupedTradeMapping groupedTradeMapping) {
		this.groupedTradeMapping = groupedTradeMapping;
	}
	
}
