package com.sapient.controller;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sapient.data.TradeData;
import com.sapient.rules.Thread.ConsumerThread;
import com.sapient.service.IScheduledReaderService;
import com.sapient.service.ITradeService;
import com.sapient.service.SAPException;
import com.sapient.service.ScheduledReaderService;
import com.sapient.service.TradeService;
import com.sapient.test.NotifyThread;

public class Controller {

	private static boolean saveDataFromFiles() throws SAPException {

		try {
			IScheduledReaderService scheduledReaderService = getSchedulerService();
			scheduledReaderService.loadFilesData(); // triggers the scheduler
			List<TradeData> tradeData = scheduledReaderService.getTradeData();

			ITradeService tService = getTradeService();
			boolean saved = tService.saveTradeData(tradeData);

			Semaphore mutex = new Semaphore(1);
			if (saved) {
				Thread notifier = new Thread(new NotifyThread(mutex, true));
				notifier.setName("EventNotifier-Thread");
				notifier.start();
			}

			Thread consumer = new Thread(new ConsumerThread(mutex, true));
			consumer.setName("Consumer-Thread");
			consumer.start();

			// tReaderService.executeRules();

		} catch (Exception e) {
			throw new SAPException("error in saving data !", e);
		}

		return false;
	}

	private static ITradeService getTradeService() {
		try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:application-main-config.xml")) {
			ITradeService tradeS = (TradeService) context.getBean("tradeService");
			return tradeS;
		}
	}

	private static IScheduledReaderService getSchedulerService() {

		try (ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:application-main-config.xml")) {
			IScheduledReaderService readerService = (ScheduledReaderService) context.getBean("scheduledReaderService");
			return readerService;
		}
	}

	public boolean start() {
		try {
			saveDataFromFiles();
		} catch (SAPException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

}
