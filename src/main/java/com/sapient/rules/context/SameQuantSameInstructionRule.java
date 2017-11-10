package com.sapient.rules.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sapient.dao.TradeDao;
import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.persistence.data.GroupedTradeMapping;
import com.sapient.service.SAPException;

	@Service("sameQuantSameInstructionRule")
	public class SameQuantSameInstructionRule implements IRuleStrategy {
		
		static final Logger logger = Logger.getLogger(TradeDao.class);

		/**
		 * group by same quantity and then same transaction details, 
		 * if MORE THAN one value found in the grouping list, then rule 
		 * violation success.
		 * @throws SAPException 
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public List<GroupOrderWarningResponse> applyRuleStrategy(List<GroupedTradeMapping> list) throws SAPException {
			Map<Integer, Map<String, List<Long>>> map = null;
			List<GroupOrderWarningResponse> response = new ArrayList<>();
			try{
				 map = list.stream().collect(Collectors.groupingBy(p -> p.getSumQuantity(), 
					Collectors.groupingBy(p->p.getOrderInstructionDetails(),
					Collectors.collectingAndThen(Collectors.toList(), l->{
						return l.stream().map(GroupedTradeMapping::getGroupId).distinct().collect(Collectors.toList());
					}))
					));
			
			for (Iterator it = map.values().iterator(); it.hasNext();) {
				Map<String, List<GroupedTradeMapping>> mp = (Map<String, List<GroupedTradeMapping>>)it.next();
				for (Iterator it2 = mp.values().iterator(); it2.hasNext();) {
					List<GroupedTradeMapping> responseList = (List<GroupedTradeMapping>) it2.next();
					
					List groupIds = responseList.stream().collect(Collectors.toList());
					String grpOneGrpTwoViolated = "Same Quantity Same Instruction Rule Transaction Code Rule violated for" + groupIds.toString();

					if(groupIds.size() > 1){ //if MORE THAN one value found in the grouping list
					GroupOrderWarningResponse groupOrderWarningResponse = 
							new GroupOrderWarningResponse(grpOneGrpTwoViolated, groupIds);
					response.add(groupOrderWarningResponse);
					}
				}
				
			}
			}catch (Exception e) {
				throw new SAPException("error while applying strategy for sameQuantSameInstructionRule!");
			}
			return response;
		}

	
		

}
