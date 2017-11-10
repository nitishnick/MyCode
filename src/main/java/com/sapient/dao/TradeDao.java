package com.sapient.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.data.TradeData;
import com.sapient.persistence.HibernateUtil;
import com.sapient.persistence.data.GroupOrderWarningData;
import com.sapient.persistence.data.GroupedTradeMapping;
import com.sapient.persistence.data.Trade;

@Repository("tradeDao")
public class TradeDao implements ITradeDao {

	static final Logger logger = Logger.getLogger(TradeDao.class);

	@Override
	public boolean saveTradeData(Map<String, Map<String, List<TradeData>>> tradeData) {

		List<TradeData> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			int i = 1;

			for (Iterator itr = tradeData.entrySet().iterator(); itr.hasNext();) {
				Entry<String, Map<String, List<TradeData>>> entry = (Entry<String, Map<String, List<TradeData>>>) itr
						.next();
				String securityIdKey = entry.getKey();

				Map<String, List<TradeData>> tCodeMappings = entry.getValue();
				for (Iterator itr2 = tCodeMappings.entrySet().iterator(); itr2.hasNext();) {
					Entry<String, List<TradeData>> tradeListEntry = (Entry<String, List<TradeData>>) itr2.next();
					String keyTCode = tradeListEntry.getKey();
					List<TradeData> tradeList = tradeListEntry.getValue();

					GroupedTradeMapping group = new GroupedTradeMapping();
					for (TradeData td : tradeList) {
						group.setAvgLimitPrice(td.getAvgLimitPrice());
						group.setOrderInstructionDetails(td.getOrderInstructionDetails());
						group.setSumQuantity(td.getSumQuantity());

						Trade trade = new Trade(td.getOrderId(), td.getSecurityId(), td.getTransactionCode(),
								td.getSymbol(), td.getQuantity(), td.getLimitPrice(), td.getOrderInstructions(), group);
						trade.setGroupedTradeMapping(group);
						group.getTrades().add(trade);

						session.save(group);
						session.save(trade);

						if (i % 50 == 0) {
							session.flush();
							session.clear();
						}
					}
				}
			}
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GroupedTradeMapping> fetchGroupedTrades() {

		List<GroupedTradeMapping> list = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Criteria query = session.createCriteria(GroupedTradeMapping.class);
			list = query.list();

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				GroupedTradeMapping mapping = (GroupedTradeMapping) iterator.next();
				Set sets = mapping.getTrades();
			}

			tx.commit();
			return list;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return Collections.emptyList();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean saveWarnings(List<GroupOrderWarningResponse> grpWarnings) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			for (Iterator it = grpWarnings.iterator(); it.hasNext();) {
				GroupOrderWarningResponse groupOrdWResponse = (GroupOrderWarningResponse) it.next();

				GroupOrderWarningData data = new GroupOrderWarningData(groupOrdWResponse.getMessage(), 0);
				session.save(data);
			}
			tx.commit();
			return true;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return false;
	}
}
