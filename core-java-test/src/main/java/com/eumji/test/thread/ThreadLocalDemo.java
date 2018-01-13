package com.eumji.test.thread;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * threadLocal 内存泄露测试
 * 与线程是否结束有关
 * 当线程没有结束,而且有没有调用remove或者set(null)方法
 * 则引用一直存在无法被回收
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-13
 * @time: 上午9:04
 */
public class ThreadLocalDemo {
    private static final ThreadLocal<Byte[]> threadLocal = new ThreadLocal<>();
    static class ThreadLocalRunnable implements Runnable{

        @Override
        public void run() {
            threadLocal.set(new Byte[1024*128]);
            System.gc();
            Byte[] bytes = threadLocal.get();
            if (bytes == null){
                System.out.println("threadLocal get null");
            }
        }
    }
    public static void main(String[] args) throws NoSuchFieldException, InterruptedException {
//        ExecutorService service = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 10000; i++) {
//            service.execute(new ThreadLocalRunnable());
//        }
//
//
//        Thread.sleep(10000L);
//        System.out.println();
//        Byte[] bytes = threadLocal.get();

        System.out.println();
        threadLocal.set(new Byte[1024*1024]);
        System.gc();
        Thread.sleep(1000L);
        threadLocal.set(null);
        System.gc();
        Thread.sleep(1000L);
        System.out.println();
    }
}
