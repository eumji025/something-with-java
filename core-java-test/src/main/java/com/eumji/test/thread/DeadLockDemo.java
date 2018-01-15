package com.eumji.test.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *
 * 死锁demo
 *
 * 运行main方法
 * 命令行输入 jps 查看所有java进程
 * 查看栈信息 jstack pid pid指代进程号
 *
 * "pool-1-thread-2":
     at com.eumji.test.thread.DeadLockDemo.lambda$main$1(DeadLockDemo.java:39)
     - waiting to lock <0x00000000d7936b48> (a java.lang.Object)
     - locked <0x00000000d7936b58> (a java.lang.Object)
     at com.eumji.test.thread.DeadLockDemo$$Lambda$2/1452126962.run(Unknown Source)
     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     at java.lang.Thread.run(Thread.java:748)
     "pool-1-thread-1":
     at com.eumji.test.thread.DeadLockDemo.lambda$main$0(DeadLockDemo.java:26)
     - waiting to lock <0x00000000d7936b58> (a java.lang.Object)
     - locked <0x00000000d7936b48> (a java.lang.Object)
     at com.eumji.test.thread.DeadLockDemo$$Lambda$1/1480010240.run(Unknown Source)
     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     at java.lang.Thread.run(Thread.java:748)

     Found 1 deadlock.

 *
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-15
 * @time: 下午10:20
 */
public class DeadLockDemo {
private static final Logger LOGGER = LoggerFactory.getLogger(DeadLockDemo.class);
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        ExecutorService executorService =Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            synchronized (o1) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    LOGGER.warn( "Interrupted!", e);
                }
                synchronized (o2) {
                    System.out.println("我进入锁了");
                }
            }
        });

        executorService.execute( () -> {
            synchronized (o2) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    LOGGER.warn( "Interrupted!", e);
                }
                synchronized (o1) {
                    System.out.println("我进入锁了");
                }
            }
        });
    }
}
