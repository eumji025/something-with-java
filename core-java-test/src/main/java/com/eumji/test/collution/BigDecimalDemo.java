package com.eumji.test.collution;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 *
 * 目前总结的三点经验
 *
 * 1.使用double转bigdecimal时候注意，很有可能会出现浮动
 *
 * 2.add,mult,devide方法也有可能出现计算值和预期值有偏差的地方
 *
 * 3.进行除法计算的时候一定要进行保留位数和进位的方式设置，，，很容易除不尽导致异常
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-26
 * @time: 下午8:09
 */
public class BigDecimalDemo {


    /**
     * string类型add方法不会丢失精度
     */
    private static void stringTest(){
        BigDecimal a = new BigDecimal("2.00");
        BigDecimal b = new BigDecimal("0.01");
        System.out.println(a.add(b)); //2.01不为丢失精度

        BigDecimal c = new BigDecimal("10.00");
        BigDecimal d = new BigDecimal("3.01");
        //System.out.println(c.divide(d)); //抛异常
        System.out.println(c.divide(d,4, ROUND_HALF_UP));//保留位数+四舍五入
    }

    /**
     * double类型的add还是会丢失精度
     */
    private static void doubleTest(){
        BigDecimal a = new BigDecimal(2.00);
        BigDecimal b = new BigDecimal(0.01);
        //value 2.01000000000000000020816681711721685132943093776702880859375
        System.out.println(a.add(b));

        System.out.println(a.multiply(b));

        //double的除法，除不尽很容易抛异常 Exception in thread "main" java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
        //针对这种情况一定要设置保留几位
        BigDecimal c = new BigDecimal(10.00);
        BigDecimal d = new BigDecimal(3.01);
        //System.out.println(c.divide(d)); //抛异常
        System.out.println(c.divide(d,4, ROUND_HALF_UP));//保留位数+四舍五入
    }

    public static void main(String[] args) {
        doubleTest();
    }
}
