package com.eumji.test.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 关于步长增长线程的测试
 *
 * jdkPoolTest方法永远都打印 `现有工作线程：xxx`
 * 因为核心线程数就是5，必须要等到队列满了才会增长，太傻了。。。。
 *
 *
 *autoworkerTest 会根据等待队列大小和 最大线程数和核心线程数只差来求得步长，等待的任务达到一个步长增加一个线程。。。
 *
 *
 */
public class AutoWorkerServiceTest {


	public static void jdkPoolTest(){
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 20, 30000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(4000));
		CountDownLatch countDownLatch = new CountDownLatch(1000);
		for (int i = 0; i < 1000; i++) {
			threadPoolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					int activeCount = threadPoolExecutor.getActiveCount();
					if (activeCount > 5){
						System.out.println("现有工作线程："+activeCount);
					}

					try {
						Thread.sleep(50L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						countDownLatch.countDown();
					}

				}
			});
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行完毕");
	}

	public static void autoworkerTest(){
		AutomaticWorkQueueImpl automaticWorkQueue = new AutomaticWorkQueueImpl(4000,0,20,5,30000L,"auto-work-threadpool-");

		CountDownLatch countDownLatch = new CountDownLatch(1000);
		for (int i = 0; i < 1000; i++) {
			automaticWorkQueue.execute(new Runnable() {
				@Override
				public void run() {

					int activeCount = automaticWorkQueue.getActiveCount();
					if (activeCount > 5){
						System.out.println("现有工作线程："+activeCount);
					}

					try {
						Thread.sleep(50L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("还剩"+automaticWorkQueue.getPoolSize());
	}

	public static void main(String[] args) throws InterruptedException {

		jdkPoolTest();

	}
}
