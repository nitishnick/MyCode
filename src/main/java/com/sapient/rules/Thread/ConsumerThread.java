package com.sapient.rules.Thread;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sapient.dao.TradeDao;
import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.rules.context.ContraTransactionCodeRule;
import com.sapient.rules.context.IRuleStrategy;
import com.sapient.rules.context.IStrategyContext;
import com.sapient.rules.context.SameQuantSameInstructionRule;
import com.sapient.rules.context.StrategyContext;
import com.sapient.service.ITradeService;
import com.sapient.service.SAPException;
import com.sapient.service.TradeService;

@Configuration("consumerThread")
public class ConsumerThread implements Runnable {

	Semaphore semaphore = null;
	static final Logger logger = Logger.getLogger(TradeDao.class);

	@Autowired
	@Qualifier("strategyContext")
	private IStrategyContext strategyContext;

	@Autowired
	@Qualifier("tradeService")
	private ITradeService tradeService;

	@Autowired
	@Qualifier("contraTransactionCodeRule")
	private IRuleStrategy contraTransactionCodeRule;

	@Autowired
	@Qualifier("sameQuantSameInstructionRule")
	private IRuleStrategy sameQuantSameInstructionRule;

	private boolean oneTimeOnly;

	public ConsumerThread() {
	}

	public ConsumerThread(Semaphore sem, boolean oneTimeOnly) {
		this.semaphore = sem;
		this.oneTimeOnly = oneTimeOnly;
	}

	public void run() {
		while (true) {
			logger.log(Priority.DEBUG, "Notifiacation Received !");
			this.semaphore.release();
			// receives signal
			try {
				List<GroupOrderWarningResponse> ruleWarningList = getStrategyContext()
						.applyRuleStrategy(getContraTransactionCodeRule());
				List<GroupOrderWarningResponse> sameQuantSameInstructionWarnings = getStrategyContext()
						.applyRuleStrategy(getSameQuantSameInstructionRule());

				ruleWarningList.addAll(sameQuantSameInstructionWarnings);

				boolean saved = getTradeService().saveWarnings(ruleWarningList);
			} catch (SAPException e) {
				e.printStackTrace();
			}

			if (oneTimeOnly) {
				return;
			}
		}
	}

	public IStrategyContext getStrategyContext() {
		if (strategyContext == null) {
			strategyContext = (StrategyContext) new ClassPathXmlApplicationContext(
					"classpath:application-main-config.xml").getBean("strategyContext");
		}
		return strategyContext;
	}

	public void setStrategyContext(IStrategyContext strategyContext) {
		this.strategyContext = strategyContext;
	}

	public ITradeService getTradeService() {
		if (tradeService == null) {
			tradeService = (TradeService) new ClassPathXmlApplicationContext("classpath:application-main-config.xml")
					.getBean("tradeService");
		}
		return tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public IRuleStrategy getContraTransactionCodeRule() {
		if (contraTransactionCodeRule == null) {
			contraTransactionCodeRule = (ContraTransactionCodeRule) new ClassPathXmlApplicationContext(
					"classpath:application-main-config.xml").getBean("contraTransactionCodeRule");
		}
		return contraTransactionCodeRule;
	}

	public void setContraTransactionCodeRule(IRuleStrategy contraTransactionCodeRule) {
		this.contraTransactionCodeRule = contraTransactionCodeRule;
	}

	public IRuleStrategy getSameQuantSameInstructionRule() {
		if (sameQuantSameInstructionRule == null) {
			sameQuantSameInstructionRule = (SameQuantSameInstructionRule) new ClassPathXmlApplicationContext(
					"classpath:application-main-config.xml").getBean("sameQuantSameInstructionRule");
		}
		return sameQuantSameInstructionRule;
	}

	public void setSameQuantSameInstructionRule(IRuleStrategy sameQuantSameInstructionRule) {
		this.sameQuantSameInstructionRule = sameQuantSameInstructionRule;
	}

}
