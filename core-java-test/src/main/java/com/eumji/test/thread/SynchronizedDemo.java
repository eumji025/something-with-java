package com.eumji.test.thread;

/**
 *
 * 用于测试同步快和同步方法的区别
 *
 * 同步快可以锁对象和类
 *
 * 而静态同步方法只能锁类
 *
 * 同步方法只能锁当前对象,同时还可以获取类锁（调用类静态方法时候会阻塞其他类锁）。
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-1
 * @time: 上午7:35
 */
public class SynchronizedDemo {

    static class MehthodDemo{

        public static synchronized void hello1() throws InterruptedException {

            System.out.println("ok!!!!!11111");
            Thread.sleep(3000L);
            System.out.println("hello ending 1111");
        }

        public static synchronized void hello5() throws InterruptedException {

            System.out.println("ok!!!!!");
            Thread.sleep(3000L);
        }

        public synchronized void hello2() throws InterruptedException {
            System.out.println("ok2!!!!");
            Thread.sleep(1000L);
            hello1();
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

    /**
     * 测试对象锁+类锁 会影响其他的类锁
     * @throws InterruptedException
     */
    public static void objectAndClass() throws InterruptedException {
        MehthodDemo mehthodDemo = new MehthodDemo();
        new Thread(()->{
            try {
                mehthodDemo.hello2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(500L);
        new Thread(()->{
            try {
                MehthodDemo.hello1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 类锁和同步块，对象同步锁之间区别和关联
     * 同步快可以锁住对象也可以锁类，非常灵活，范围可控制
     * 同步方法也是可以重入类锁的，，，需要注意
     * 类锁只能锁类，不影响对象
     */
    public static void lockTest() {
        MehthodDemo mehthodDemo = new MehthodDemo();

        new Thread(()-> {
            try {
                MehthodDemo.hello1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            try {
                mehthodDemo.hello33();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            try {
                mehthodDemo.hello3();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {

        objectAndClass();



    }
}
