package com.sapient.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sapient.dao.ITradeDao;
import com.sapient.dao.TradeDao;
import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.data.TradeData;


@Service("tradeService")
public class TradeService implements ITradeService {

	static final Logger logger = Logger.getLogger(TradeDao.class);

	@Value("${trade.files.path}")
	private String tradeFilesPath;
	
	@Autowired
	@Qualifier("tradeDao")
	private ITradeDao tradeDao;

	// OrderId SecurityId Transaction Code Symbol Quantity Limit Price Order
	// Instructions
	/**
	 * It saves but before saving it does group by.
	 */
	@Override
	public boolean saveTradeData(List<TradeData> tradeData) {

		Map<String, Map<String, List<TradeData>>> map  = groupByTradeData(tradeData);
		boolean saved = saveGroupedData(map);
		return saved;
	}



	private boolean saveGroupedData(Map<String, Map<String, List<TradeData>>> tradeData) {

		try {
		return 	tradeDao.saveTradeData(tradeData);
		} catch (Exception e) {
		}
		return false;
	}


	private Collector<TradeData, ?, Map<String, List<TradeData>>> groupByTrasactionCode(){
		return Collectors.groupingBy(p->p.getTransactionCode());
	}
	
	private Map<String, Map<String, List<TradeData>>> groupByTradeData(List<TradeData> tradeData) {
		try {
			Map<String, Map<String, List<TradeData>>> map = tradeData.stream()
					.collect(Collectors.groupingBy(p -> p.getSecurityId(),
							Collectors.groupingBy(p->p.getTransactionCode(),
									Collectors.collectingAndThen(Collectors.toList(), l->{
										
									      int total=l.stream().collect(Collectors.summingInt(TradeData::getQuantity));//r -> r.getQuantity()
									      double avg = l.stream().collect(Collectors.summingDouble(r -> {
									      	return (r.getQuantity()*r.getLimitPrice());
									      			}));
									      String joinedString = l.stream().map(p->p.getOrderInstructions()).collect(Collectors.joining(","));
									      l.forEach(r->r.setSumQuantity(total));
									      l.forEach(r->r.setAvgLimitPrice(avg/total));
									      l.forEach(r->r.setOrderInstructionDetails(joinedString));
									      return l;
									      
									}))
							));
			return map;
			
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public boolean saveWarnings(List<GroupOrderWarningResponse> ruleWarnings) {
		boolean success = false;
		try{
			return success = tradeDao.saveWarnings(ruleWarnings);
		}catch (Exception e) {
		}
		
		
		return success;
	}

}