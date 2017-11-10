package com.sapient.dao;

import java.util.List;
import java.util.Map;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.data.TradeData;
import com.sapient.persistence.data.GroupedTradeMapping;

public interface ITradeDao
{

	boolean saveTradeData(Map<String, Map<String, List<TradeData>>> tradeData);

	List<GroupedTradeMapping> fetchGroupedTrades();

	boolean saveWarnings(List<GroupOrderWarningResponse> ruleWarnings);
	
	

}