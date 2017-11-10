package com.sapient.service;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.sapient.data.TradeData;

@Configuration("scheduledReaderService")
@EnableScheduling
public class ScheduledReaderService implements IScheduledReaderService {

	private List<String> listOfFilePaths = new ArrayList<>();

	@Value("${scheduler.fixedDelay}")
	private long fixedDelay;
	
	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); // single threaded by default
	}

	@Value("${trade.files.path}")
	private String consolidatedPathsFilePath;

	private List<TradeData> tradeDataList = new ArrayList<>();

	 @Scheduled(fixedDelay = 9000)
	public void loadFilesData() throws SAPException {
		System.out.println("......here....");
		List<String> newFilesToRead = null;
		try {

			if (this.consolidatedPathsFilePath == null || this.consolidatedPathsFilePath.isEmpty()) {
				throw new SAPException("property value missing in .props file!");
			}

				newFilesToRead = readNewFilePathsFromConsolidated(consolidatedPathsFilePath, false);
	//		validate(); // to validate the data inside the file
 		} catch (Exception e) {
			throw new SAPException("", e);
		}

		ExecutorService eService = Executors.newFixedThreadPool(2);
		// tradeDataList = readAllFilesData(newFilesToRead);
		List<Task> callables = new ArrayList<>();
		for (String path : newFilesToRead) {
			Task a = new Task(path);
			callables.add(a);
		}
		try {
			List<Future<List<TradeData>>> futures = eService.invokeAll(callables);
			for (Future<List<TradeData>> future : futures) {

				List<TradeData> trades;
				trades = future.get();
				tradeDataList.addAll(trades);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		eService.shutdown();
	}
	/**
	 * Read each path from consolidated file of paths.
	 * @param consolidatedFilePaths
	 * @return
	 * @throws SAPException
	 */
	private List<String> readAllFilePathsFromConsolidated(String consolidatedFilePaths) throws SAPException {
		return readNewFilePathsFromConsolidated(consolidatedFilePaths, true);
	}

	private List<String> readNewFilePathsFromConsolidated(String consolidatedFilePaths, boolean readAll)
			throws SAPException {

		List<String> freshFilePaths =  new ArrayList<>();
		List<String> netFilePathsToRead = null;
		try(BufferedReader bReader = Files.newBufferedReader(Paths.get(consolidatedFilePaths))	) {
			freshFilePaths = bReader.lines().collect(Collectors.toList());
			if (freshFilePaths.isEmpty()) {
				throw new SAPException("file paths not there in consolidated files !");
			}
			netFilePathsToRead = freshFilePaths.stream().map( p -> p.split("=")[1]).collect(Collectors.toList());
			if (readAll) { // first time
//				tradeDataList.clear();
			} else {
				
				List<String> deltaOnlyNewlyAddedFilePaths =  netFilePathsToRead.stream().filter(p -> !(listOfFilePaths.contains(p))).collect(Collectors.toList());
				netFilePathsToRead = deltaOnlyNewlyAddedFilePaths;
				this.listOfFilePaths = netFilePathsToRead.size()>0 ? netFilePathsToRead:this.listOfFilePaths;
			}
		} catch (Exception e) {
			throw new SAPException("Error in file reading.", e);
		}

		return netFilePathsToRead;
	}

	public List<String> getListOfFilePaths() {
		return listOfFilePaths;
	}

	static class Task implements Callable<List<TradeData>> {
		String path;

		public Task(String path) {
			this.path = path;
		}

		private List<TradeData> readAllFilesData(String filePath) throws SAPException {
			// OrderId SecurityId Transaction Code Symbol Quantity Limit Price
			// Order
			// Instructions
			List<TradeData> dataList = new ArrayList<>();
			try (Scanner sc = new Scanner(new File(filePath))) {
				boolean first = true;
				while (sc.hasNextLine()) {
					TradeData data = new TradeData();
					String line = sc.nextLine();
					if (first) {
						first = false;
						continue;

					}
					String[] row = line.split(",");
					if (row[0].equalsIgnoreCase(("orderid")))
						continue;
					data.setOrderId(Long.parseLong(row[0]));

					data.setSecurityId(row[1]);
					String tempCode = row[2];
					if (tempCode.equals("B") || tempCode.equalsIgnoreCase("Buy"))
						data.setTransactionCode("Buy");
					else if (tempCode.equals("S") || tempCode.equalsIgnoreCase("Sell"))
						data.setTransactionCode("Sell");

					data.setSymbol(row[3]);
					data.setQuantity(Integer.parseInt(row[4]));
					data.setLimitPrice(Double.parseDouble(row[5]));
					data.setOrderInstructions(row[6]);
					dataList.add(data);
				}
			} catch (Exception e) {
				throw new SAPException("", e);
			}
			return dataList;
		}

		@Override
		public List<TradeData> call() throws Exception {

			try {
				return readAllFilesData(this.path);
			} catch (SAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Collections.emptyList();
		}
	}

		public List<TradeData> getTradeData() {
			return tradeDataList;
		}

}