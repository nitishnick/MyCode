package com.sapient.rules.context;

import java.util.List;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.service.SAPException;

public interface IStrategyContext {

	public  List<GroupOrderWarningResponse>  applyRuleStrategy(IRuleStrategy ruleStrategy) throws SAPException;
}
