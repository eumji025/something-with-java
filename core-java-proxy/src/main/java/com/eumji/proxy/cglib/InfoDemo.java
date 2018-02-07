package com.eumji.proxy.cglib;

/**
 * @author eumji
 * @date 2018/2/7
 */
public  class InfoDemo {

    public void welcome (String person){

        System.out.println("welcome :" + person);
        //return "welcome:"+person;
    }

    public final void welcome2 (String person){

        System.out.println("welcome :" + person);
        //return "welcome:"+person;
    }
}
