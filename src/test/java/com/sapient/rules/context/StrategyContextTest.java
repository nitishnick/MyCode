package com.sapient.rules.context;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sapient.data.GroupOrderWarningResponse;
import com.sapient.service.SAPException;

public class StrategyContextTest {

	
	IStrategyContext strategyContext = null;
	
	IRuleStrategy ruleS = null;

	
    @Before
    public void initData() {
    	System.out.println();
    	strategyContext = (StrategyContext) new ClassPathXmlApplicationContext(
				"classpath:application-main-config.xml").getBean("strategyContext");
    }
    
    @Test
	public  void  givenStrategyContext_applySameQuantSameInstructionRuleStrategy() throws SAPException{
    	ruleS = new SameQuantSameInstructionRule();
    	strategyContext.applyRuleStrategy(ruleS);
	}

    
//    @Test
	public  void  givenStrategyContext_applyContraTransactionOrderRuleStrategy() throws SAPException{
    	ruleS = new ContraTransactionCodeRule();
    	strategyContext.applyRuleStrategy(ruleS);
	}
    
}
