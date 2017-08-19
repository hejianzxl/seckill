package org.seckill.core.factory;

import java.util.concurrent.Executors;
public class ScheduledTaskFactory {
	public static void register(Runnable r) {
		Executors.newFixedThreadPool(20).execute(r);
	}
}
