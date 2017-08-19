package org.seckill.core.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

	private static ExecutorService executorService = Executors.newFixedThreadPool(5);

	private ThreadUtils() {

	}

	public static ThreadUtils getInstance() {
		return ThreadHandler.instance;
	}
	
	public ExecutorService defaultExecutor() {
		return executorService;
	}

	static class ThreadHandler {
		private static ThreadUtils instance = new ThreadUtils();
	}

}
