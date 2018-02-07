package com.eumji.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author eumji025
 * @date 2018/2/7
 */
public class CglibInfoProxy implements MethodInterceptor {

    private Object target;

    public Object newInstance(Object source){

        target = source;
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);

         return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method!!!");

        Object value = methodProxy.invoke(o, objects);
        //Object value = methodProxy.invoke(this.target, objects);
        //Object value = methodProxy.invokeSuper(o, objects);
        return value;
    }
}
