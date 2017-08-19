package org.seckill.core;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.print.attribute.standard.MediaName;


public class Demo {

	BlockingQueue<String>	queue			= new LinkedBlockingQueue<String>();

	ExecutorService			executorService	= Executors.newFixedThreadPool(20);

	private Object			object			= new Object();
	
	public void test() {
		executorService.execute(new QueryThread(queue));
		executorService.execute(new InsertThread(queue));
	}

	class QueryThread implements Runnable {

		BlockingQueue queue = null;

		public QueryThread(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				synchronized (object) {
					// 查询数据库
					for (;;) {
						queue.add("对象");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class InsertThread implements Runnable {

		BlockingQueue queue = null;

		public InsertThread(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				for (;;) {
					Thread.sleep(10);
					String data = (String) queue.take();
					// TODO insert db
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public static void main(String[] args) {
		System.out.println(1000%5);
		System.out.println(32131231%5);
	}
}
