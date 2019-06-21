package com.eumji.test.jvm;

/**
 * 看说明 https://jingyan.baidu.com/article/f71d6037a1af2d1ab641d112.html
 *
 * 用于定位线程狂占CPU的问题
 */
public class JstackThreadDemo {

    public static void main(String[] args) {
        new Thread(() -> {
            while (true){
                System.out.println("thread name is :"+Thread.currentThread().getName());
            }
        }).start();
    }
}
