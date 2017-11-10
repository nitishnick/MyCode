package com.sapient.rules.context;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sapient.dao.ITradeDao;
import com.sapient.dao.TradeDao;
import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.persistence.data.GroupedTradeMapping;
import com.sapient.service.ITradeService;
import com.sapient.service.SAPException;

/**
 * fetch data from group table and apply rule
 * 
 * @author nrohil
 *
 */
@Service("strategyContext")
public class StrategyContext implements IStrategyContext {
	static final Logger logger = Logger.getLogger(TradeDao.class);

	@Autowired
	private ITradeService tradeService;

	@Autowired
	@Qualifier("tradeDao")
	private ITradeDao tradeDao;

	private List<GroupedTradeMapping> fetchGroupedTrades() {
		return tradeDao.fetchGroupedTrades();
	}

	private List<GroupedTradeMapping> groupedTrList;

	public List<GroupOrderWarningResponse> applyRuleStrategy(IRuleStrategy ruleStrategy) throws SAPException {
		if (groupedTrList == null || groupedTrList.isEmpty())
			groupedTrList = fetchGroupedTrades();

		return ruleStrategy.applyRuleStrategy(groupedTrList);
	}

}
