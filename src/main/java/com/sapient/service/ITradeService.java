package com.sapient.service;

import java.util.List;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.data.TradeData;

public interface ITradeService {
	boolean saveTradeData(List<TradeData> tradeData);

	boolean saveWarnings(List<GroupOrderWarningResponse> contraGrpWarnings);


}
