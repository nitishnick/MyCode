package com.sapient.persistence.data;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity(name="GroupedTradeMapping")
@Table(name = "grouped_trade_mapping", catalog = "tradingdb")
public class GroupedTradeMapping implements java.io.Serializable {


	private long groupId;
	
	private int sumQuantity;

	private double avgLimitPrice;

	private String orderInstructionDetails;

	private Set<Trade> trades = new HashSet<Trade>(0);

	public GroupedTradeMapping() {
	}

	public GroupedTradeMapping(long groupId, long orderId, int sumQuantity, double avgLimitPrice,
			String orderInstructionDetails,Set<Trade> trades) {
		this.sumQuantity = sumQuantity;
		this.avgLimitPrice = avgLimitPrice;
		this.orderInstructionDetails = orderInstructionDetails;
		this.trades=trades;
	}
	
	@Column(name = "sum_quantity")
	public int getSumQuantity() {
		return sumQuantity;
	}

	public void setSumQuantity(int sumQuantity) {
		this.sumQuantity = sumQuantity;
	}
	
	@Column(name = "avg_limit_price")
	public double getAvgLimitPrice() {
		return avgLimitPrice;
	}

	public void setAvgLimitPrice(double avgLimitPrice) {
		this.avgLimitPrice = avgLimitPrice;
	}
	
	@Column(name = "order_instructions_joined")
	public String getOrderInstructionDetails() {
		return orderInstructionDetails;
	}

	public void setOrderInstructionDetails(String orderInstructionDetails) {
		this.orderInstructionDetails = orderInstructionDetails;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "group_id", unique = true, nullable = false)
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	  
	@OneToMany(mappedBy = "groupedTradeMapping")
	@Fetch(FetchMode.JOIN)
	public Set<Trade> getTrades() {
		return trades;
	}

	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}
	
}
