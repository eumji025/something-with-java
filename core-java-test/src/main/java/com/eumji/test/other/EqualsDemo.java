package com.eumji.test.other;

/**
 *
 * 关于常量的测试
 * 也就是会不会新创建对象的问题
 *
 * 使用new 始终都会在堆上分配一个新的对象
 *
 * string.intern方法会尝试先从常量池获取，如果不存在的话就会将常量池的对象和堆里面的对象指向同一个。 可以看equals2方法
 *
 * @author eumji
 * @date 2018/3/12
 */
public class EqualsDemo {
    public static final String JAAVA = "javva";


    public static void equalsStr(){
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        System.out.println(s1 == s2);  // true
        System.out.println(s1 == s3);  // true
        System.out.println(s1 == s4);  // false
        System.out.println(s1 == s9);  // false
        System.out.println(s4 == s5);  // false
        System.out.println(s1 == s6);  // true
    }

    /**
     * int类型的比较方法
     * 直接等于数字 会分配在常量池
     * new integer 大于127会是创建在堆
     * 小于127会直接调用Integer.valueOf()方法 然后由于有integerCache 所以指向的还是常量池
     *   编译后的代码
     Integer a = 121212;
     Integer b = 121212;
     new Integer(121212);
     Integer low = Integer.valueOf(12);
     Integer low2 = Integer.valueOf(12);
     new Integer(12);
     System.out.println();
     */
    public static void equalsInt(){

        Integer a = 121212;
        Integer b = 121212;
        Integer c = new Integer(121212);


        Integer low = 12;
        Integer low2 = 12;

        Integer low3 = new Integer(12);
        System.out.println(low2 == low3);
    }

    public static void equals2(){
        /**
         * 为什么会为true 粗略的理解是这样子的，首先
         */
        String append = new StringBuilder().append("计算机").append("jishu").toString();
        String demo = "计算机jishu";
        String intern = append.intern();
        //System.out.println(append.intern() == append); // true 因为

        String append2 = new StringBuilder().append("jav").append("va").toString();
        String intern2 = append2.intern();
        String append3 = "javva";
        System.out.println(append2.intern() == append2);//false 因为常量池已经存在javva了，所以intern就会指向原来存在的常量池

        System.out.println(append2 == "javva");
    }

    public static void main(String[] args) {

        //equalsStr();
        //equals2();
        equalsInt();

    }
}
