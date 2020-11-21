package com.eumji.jvm.object;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;


/**
 * jdk锁介绍：
 * https://www.jb51.net/article/197704.htm
 */
public class JvmObjectDemo {

    private String a;
    private Integer b;

    private long c;

    public static void main(String[] args) {
        JvmObjectDemo jvmObjectDemo = new JvmObjectDemo();
        System.out.println("=======查看当前的对象地址=======");
        System.out.println(VM.current().addressOf(jvmObjectDemo));

        //JVM内存结构信息
        /**
         * Jdk1.8默认开启指针压缩后为4字节
         *
         *  64位虚拟机
         * # Running 64-bit HotSpot VM.
         * //oop大小进行压缩
         * # Using compressed oop with 3-bit shift.
         * //klass 进行压缩
         * # Using compressed klass with 3-bit shift.
         * //对象按8字节对齐
         * # Objects are 8 bytes aligned.
         * //string,boolean,char,short,byte,int,float,double,long
         * # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         * //对于的数字大小占用
         * # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         *
         *
         * -XX:-UseCompressedOops 关闭压缩
         * # Running 64-bit HotSpot VM.
         * # Objects are 8 bytes aligned.
         * # Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         * # Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
         *
         */
        System.out.println(VM.current().details());

        /**
         * 注意大小端存储的问题
         * com.eumji.jvm.object.JvmObjectDemo@1b4fb997 十六进制哈希：1b4fb997
         * com.eumji.jvm.object.JvmObjectDemo object internals:
         *  OFFSET  SIZE                TYPE DESCRIPTION                               VALUE
         *       0     4                     (object header)                           01 97 b9 4f (00000001 10010111 10111001 01001111) (1337562881)
         *       4     4                     (object header)                           1b 00 00 00 (00011011 00000000 00000000 00000000) (27) - 27位没有使用
         *       8     4                     (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4    java.lang.String JvmObjectDemo.a                           null
         *      16     8                long JvmObjectDemo.c                           0
         *      24     4   java.lang.Integer JvmObjectDemo.b                           null
         *      28     4                     (loss due to the next object alignment)
         *
         * tostring的地址和头里面的是保持一致的
         * 第1个4字节标识tostring的地址
         * 第2个4字节标识
         * 第3个4字节标识klass信息
         *
         * 然后变量的顺序不一定按照申明的顺序
         * 上文中因为12字节头+4字节的A已经对齐对象
         * 后面选择先布局8字节的c，在布局4字节的b
         *
         * 对象头8位我们看一下，由于是小端存储，所以每个4字节都是倒序
         * 00000000 00000000 00000000 00011011 01001111 10111001 10010111 00000001
         * ---------------27位不使用------（1b     4f       b9       97）hashcode
         * 最后八位
         * 第1位未使用，第1-5分代GC值,第6位偏向锁，第7-8锁标识
         *
         */
        System.out.println("========对象信息================");
        System.out.println(jvmObjectDemo + " 十六进制哈希：" + Integer.toHexString(jvmObjectDemo.hashCode()));
        System.out.println(ClassLayout.parseInstance(jvmObjectDemo).toPrintable());


        /**
         * 其实这里并不是偏向锁，而是轻量级锁
         * 所以感觉偏向锁很难产生,并且jdk已经题案删掉偏向锁
         * https://openjdk.java.net/jeps/374
         */
        synchronized (jvmObjectDemo){
            System.out.println("尝试偏向锁加锁的时候");
            System.out.println(ClassLayout.parseInstance(jvmObjectDemo).toPrintable());

        }


        //对于加了synchronized关键字后则会加锁
        //轻量锁情况
        /**
         *
         *  OFFSET  SIZE        TYPE DESCRIPTION   VALUE
         *       0     4        (object header)    80 f5 8f 7f (10000000 11110101 10001111 01111111) (2140140928)
         *       4     4        (object header)    88 00 00 00 (10001000 00000000 00000000 00000000) (136)
         *       8     4        (object header)    05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *
         *  轻量级锁markword
         *  62位对应线程栈的锁记录 最后2位锁状态00标识轻量级锁
         */

        jvmObjectDemo.a();




    }

    public synchronized  void a(){
        System.out.println(ClassLayout.parseInstance(this).toPrintable());
        System.out.println("current ThreadId:"+Thread.currentThread().getId());
        System.out.println("=========method A===============");
        b();
    }

    public synchronized  void b(){
        Thread thread = Thread.currentThread();
        System.out.println(ClassLayout.parseInstance(this).toPrintable());
        System.out.println("current ThreadId:"+thread.getId());
        //76af05d38
        System.out.println(VM.current().addressOf(thread));
        System.out.println(thread + " 十六进制哈希：" + Integer.toHexString(thread.hashCode()));
        System.out.println("current Thread print");
        System.out.println(ClassLayout.parseInstance(thread).toPrintable());
        System.out.println("=========method B===============");
    }
}
