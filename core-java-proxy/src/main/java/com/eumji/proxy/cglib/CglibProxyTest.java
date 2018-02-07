package com.eumji.proxy.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 *
 * 居然发生了StackOverflowError
 *
 * 原因不明
 *
 * @author lenovo
 * @date 2018/2/7
 */
public class CglibProxyTest {


    public static void main(String[] args) {

//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(InfoDemo.class);
//        enhancer.setCallback(new CglibInfoProxy());
//        InfoDemo instance = (InfoDemo) enhancer.create();


        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\\\classes");
        InfoDemo instance = (InfoDemo) new CglibInfoProxy().newInstance(new InfoDemo());

        instance.welcome("zhangsan");
    }
}
