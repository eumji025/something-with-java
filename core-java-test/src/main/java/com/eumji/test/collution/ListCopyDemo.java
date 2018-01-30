package com.eumji.test.collution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * 
 * 验证list.toArray方法
 * 加入指定了数组，但是数组长度小于list长度，则不会将元素复制到指定的数组
 * 而是会生成新的数组
 * 
 * 如果长度大于list长度，则可以复制到指定的数组中
 * strings 和strings1指向同一个地址
 *
 *
 * 产生的原因看内部实现
 *
     public <T> T[] toArray(T[] a) {
         if (a.length < size)
         // Make a new array of a's runtime type, but my contents:
         return (T[]) Arrays.copyOf(elementData, size, a.getClass());
         System.arraycopy(elementData, 0, a, 0, size);
         if (a.length > size)
         a[size] = null;
         return a;
     }
 *
 *
 *
 * 但是在集合类和祖塞队列中不会发生这样的状况
 * 因为底层使用的add方法。
 * 
 * 
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-30
 * @time: 下午4:21
 */
public class ListCopyDemo {

    public static void blockQueueTest() {
        BlockingQueue queues = new ArrayBlockingQueue<String>(10);
        queues.add("aaa");
        queues.add("aaa2");
        queues.add("aaa3");
        queues.add("aaa4");
        queues.add("aaa5");
        queues.add("aaa6");
        queues.add("aaa7");
        queues.add("aaa8");
        queues.add("aaa9");

        List<String> lists = new ArrayList<>(4);
        int count = queues.drainTo(lists);

        System.out.println();


    }

    public static void arrayCopyTest(){
        List<String> lists = new ArrayList<>();
        lists.add("aaa");
        lists.add("aaa1");
        lists.add("aaa2");
        lists.add("aaa3");
        lists.add("aaa4");
        lists.add("aaa5");
        lists.add("aaa6");
        lists.add("aaa7");
        lists.add("aaa8");
        lists.add("aaa9");

        String[] strings = new String[8];
        //String[] strings = new String[15];

        String[] strings1 = lists.toArray(strings);

    }

    public static void main(String[] args) {

    }
}
