package com.eumji.test.thread;

/**
 *
 * InheritableThreadLocal 一个父子线程间可以共享值的类
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


    public static void main(String[] args) {

        local.set("aaaaa");

        // =========父子线程下，值是共享的。
        new Thread(()->{
            System.out.println(local.get());
        }).start();


        local.set(null);

        //=========演示非父子线程的情况下，值不共享
        new Thread(()->{
            local.set("cccccccc");
            System.out.println(local.get());
        }).start();

        new Thread(()->{
            System.out.println(local.get());
        }).start();


        // ====== 子线程的修改无法影响父线程的读取
        System.out.println(local.get());


    }
}
