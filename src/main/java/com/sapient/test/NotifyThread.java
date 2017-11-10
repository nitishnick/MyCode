package com.sapient.test;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.sapient.dao.TradeDao;

public class NotifyThread implements Runnable{
	private Semaphore semaphore;
	private boolean oneTimeSignalOnly;
	static final Logger logger = Logger.getLogger(TradeDao.class);

	public NotifyThread() {
	}
	
	public NotifyThread(Semaphore semaphore, boolean oneTimeSignalOnly) {
		this.semaphore=semaphore;
		this.oneTimeSignalOnly = oneTimeSignalOnly;
	}
	
	
	@Override
	public void run() {
		 while(true){
			 logger.log(Priority.DEBUG, "Notification Signal Sent !");
			 try {
				this.semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 if(oneTimeSignalOnly){
				 return;
			 }
		    }
	}

}
