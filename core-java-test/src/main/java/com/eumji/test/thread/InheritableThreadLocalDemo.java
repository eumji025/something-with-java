package com.eumji.test.thread;

/**
 *
 * InheritableThreadLocal 一个父子线程间可以共享值的类
 *
 * 逻辑很简单
 * 就是new thread时候，会把父线程的inheritableThreadLocals值复制到子类
 *
 * 其他都没什么变化
 *
 * 此处是一个简单的验证
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-2
 * @time: 下午10:33
 */
public class InheritableThreadLocalDemo {

    private static ThreadLocal<String> local = new InheritableThreadLocal<>();


    public static void main(String[] args) throws InterruptedException {

        local.set("aaaaa");

        // =========父子线程下，值是共享的。
        new Thread(()->{
            System.out.println(local.get());
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //第二次不变说明赋值后不会在影响子线程
            System.out.println("thread get again, the value is: "+local.get());
        }).start();


        local.set("bbbb");
        Thread.sleep(501L);

        //========演示非父子线程的情况下，值不共享
        new Thread(()->{
            try {
                local.set("cccccccc");
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(local.get());
        }).start();

        //local.set("我是最新的");

        Thread.sleep(200L);

        // ======== 另一个线程是读不到的
        new Thread(()->{
            System.out.println(local.get());
        }).start();

        Thread.sleep(200L);

        // ====== 同时子线程的修改无法影响父线程的读取
        System.out.println(local.get());


    }
}
