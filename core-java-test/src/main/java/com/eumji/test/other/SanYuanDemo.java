package com.eumji.test.other;

import java.math.BigDecimal;

/**
 * 对于三元表达式而言，存在多态的情况下：
 *
 * 存在类型不一致的情况下，可能存在转换
 * 比如
 * Number result = true ? new Integer(1) : new Float(2F);
 *
 * Number result = true ? new Integer(1F) : new Integer(2);
 *
 * Number result = true ? new Double(1) : new Float(2.0D);
 *
 * 基本类型中 默认都会往大的类型转换。
 *
 * Double -> Float -> Long -> Int -> Short -> Byte
 *
 * 但是对于 bigdecimal 和基本类型 不会发生转换
 */
public class SanYuanDemo {



    /*
      0 new #2 <java/lang/Integer>
     3 dup
     4 iconst_1
     5 invokespecial #3 <java/lang/Integer.<init>>
     8 invokevirtual #4 <java/lang/Integer.intValue>
    11 i2f
    12 invokestatic #5 <java/lang/Float.valueOf>
    15 astore_1
    16 getstatic #6 <java/lang/System.out>
    19 aload_1
    20 invokevirtual #7 <java/io/PrintStream.println>

    23 new #2 <java/lang/Integer>
    26 dup
    27 iconst_1
    28 invokespecial #3 <java/lang/Integer.<init>>
    31 invokevirtual #4 <java/lang/Integer.intValue>
    34 i2f
    35 invokestatic #5 <java/lang/Float.valueOf>
    38 astore_1
    39 getstatic #6 <java/lang/System.out>
    42 aload_1
    43 invokevirtual #7 <java/io/PrintStream.println>

    46 new #8 <java/lang/Float>
    49 dup
    50 ldc #9 <1.1>
    52 invokespecial #10 <java/lang/Float.<init>>
    55 invokevirtual #11 <java/lang/Float.floatValue>
    58 f2d
    59 invokestatic #12 <java/lang/Double.valueOf>
    62 astore_1
    63 getstatic #6 <java/lang/System.out>
    66 aload_1
    67 invokevirtual #7 <java/io/PrintStream.println>

    70 new #13 <java/lang/Long>
    73 dup
    74 lconst_1
    75 invokespecial #14 <java/lang/Long.<init>>
    78 astore_1
    79 getstatic #6 <java/lang/System.out>
    82 aload_1
    83 invokevirtual #7 <java/io/PrintStream.println>
    86 return



     */
    public static void main(String[] args) {

        Number result = true ? new Integer(1) : new Float(2.1F);
        System.out.println(result);//1.0 float


        result = false ?  new Float(2.1F):new Integer(1);
        System.out.println(result);//1.0 float

        result = false ?  new Double(2.1D):new Float(1.1F);
        System.out.println(result);//1.100000023841858 double

        result = false ?  new BigDecimal(2.1D):new Long(1);
        System.out.println(result);//1  long


    }
}

