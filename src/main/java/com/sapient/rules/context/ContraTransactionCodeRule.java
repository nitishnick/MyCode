package com.sapient.rules.context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.persistence.data.GroupedTradeMapping;
import com.sapient.persistence.data.Trade;

@Service("contraTransactionCodeRule")
public class ContraTransactionCodeRule implements IRuleStrategy {

	/**
	 * In the grouped table, for one SUM, there can be either Buy or Sell
	 * 
	 */
	@Override
	public List<GroupOrderWarningResponse> applyRuleStrategy(List<GroupedTradeMapping> list) {
		List<GroupOrderWarningResponse> response = new ArrayList<>();
		try {
			int size = list.size();
			int i = 0;
			for (int j = 0; j < size - 1; j++) {
				GroupedTradeMapping gTM1 = (GroupedTradeMapping) list.get(j);
				GroupedTradeMapping gTM2 = (GroupedTradeMapping) list.get(j + 1);
				if (gTM1.getGroupId() != gTM2.getGroupId()) {
					// check whether both have same sumQ
					if (gTM1.getSumQuantity() == gTM2.getSumQuantity()) {
						Trade tradeforGp1 = gTM1.getTrades().stream().limit(1).collect(Collectors.toList()).get(0);

						Trade tradeforGp2 = gTM2.getTrades().stream().limit(1).collect(Collectors.toList()).get(0);

						if (tradeforGp1.getSecurityId().equalsIgnoreCase(tradeforGp2.getSecurityId()) && !(tradeforGp1
								.getTransactionCode().equalsIgnoreCase(tradeforGp2.getTransactionCode()))) {

							String grpOneGrpTwoViolated = "Contra Transaction CodeRule" + gTM1.getGroupId() + " and "
									+ gTM2.getGroupId();
							List<Long> list3 = new ArrayList<>();
							list3.add(gTM1.getGroupId());
							list3.add(gTM2.getGroupId());

							GroupOrderWarningResponse r = new GroupOrderWarningResponse(grpOneGrpTwoViolated, list3);
							response.add(r);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
}
