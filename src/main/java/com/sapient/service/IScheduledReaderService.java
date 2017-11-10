package com.sapient.service;

import java.util.List;

import com.sapient.data.TradeData;

public interface IScheduledReaderService  {
	public void loadFilesData() throws SAPException ;

	public List<TradeData> getTradeData();


}