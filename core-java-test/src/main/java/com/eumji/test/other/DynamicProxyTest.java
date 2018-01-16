package com.eumji.test.other;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * 文章 http://blog.csdn.net/jsu_9207/article/details/51394697
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-16
 * @time: 下午10:34
 */
public class DynamicProxyTest {

    interface Ihello{
        void sayHello();
    }

    static class Hello implements Ihello{

        @Override
        public void sayHello() {
            System.out.println("hello world!");
        }

    }

    static class DynamicProxy implements InvocationHandler {

        Object originalObj;

        Object bind(Object originalObj) {
            this.originalObj = originalObj;
            return Proxy.newProxyInstance(originalObj.getClass().getClassLoader(), originalObj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(originalObj, args);
        }

    }

    public static void main(String[] args) {
        //用于生成代理类文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Ihello hello = (Ihello) new DynamicProxy().bind(new Hello());
        hello.sayHello();

    }

}

