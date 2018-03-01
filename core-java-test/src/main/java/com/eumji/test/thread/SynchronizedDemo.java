package com.eumji.test.thread;

/**
 *
 * 用于测试同步快和同步方法的区别
 *
 * 同步快可以锁对象和类
 *
 * 而静态同步方法只能锁类
 *
 * 同步方法只能锁当前对象。
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-1
 * @time: 上午7:35
 */
public class SynchronizedDemo {

    static class MehthodDemo{

        public static synchronized void hello1() throws InterruptedException {

            System.out.println("ok!!!!!");
            Thread.sleep(3000L);
        }

        public static synchronized void hello5() throws InterruptedException {

            System.out.println("ok!!!!!");
            Thread.sleep(3000L);
        }

        public synchronized void hello2() throws InterruptedException {
            System.out.println("ok2!!!!");
            Thread.sleep(3000L);
        }

        public synchronized void hello4() throws InterruptedException {
            System.out.println("ok4!!!!");
            Thread.sleep(3000L);
        }

        public void hello3() throws InterruptedException {
            synchronized (MehthodDemo.class) {
                System.out.println(111);
                Thread.sleep(3000L);
            }
        }

        public void hello33() throws InterruptedException {
            synchronized (MehthodDemo.class) {
                System.out.println(222222);
                Thread.sleep(3000L);
            }
        }
    }

    public static void main(String[] args) {

        MehthodDemo mehthodDemo = new MehthodDemo();
        new Thread(()-> {
            try {
                MehthodDemo.hello1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

//        new Thread(()-> {
//            try {
//                mehthodDemo.hello33();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

        new Thread(()-> {
            try {
                mehthodDemo.hello3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
