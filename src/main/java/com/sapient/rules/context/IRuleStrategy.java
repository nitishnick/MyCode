package com.sapient.rules.context;

import java.util.List;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.data.TradeData;
import com.sapient.persistence.data.GroupedTradeMapping;
import com.sapient.service.SAPException;

public interface IRuleStrategy {

	List<GroupOrderWarningResponse> applyRuleStrategy(List<GroupedTradeMapping> list) throws SAPException;
}
