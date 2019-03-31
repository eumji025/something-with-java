package com.eumji.test.collution;

import java.util.*;

/**
 *
 * 关于集合for循环下移除的实验
 *
 *
 *
 * @email eumji025@gmail.com
 * @author:EumJi
 * @date: 2019/3/31
 * @time: 12:38
 */
public class ListDemo {


    private static List<Integer> arrList =new ArrayList<>();

    static {
        arrList.add(1);
        arrList.add(2);
        arrList.add(3);
        arrList.add(4);
        arrList.add(5);
        arrList.add(6);
        arrList.add(7);
        arrList.add(8);
        arrList.add(9);
        arrList.add(10);
    }

    /**
     * 使用foreach对集合进行移除
     *
     * foreach是java提供的语法糖，但是要注意其实现是借用iterator来实现的
     *
     * Iterator var1 = arrList.iterator();
     *
     *         while(var1.hasNext()) {
     *             Integer value = (Integer)var1.next();
     *             arrList.remove(value);
     *         }
     *
     * @see AbstractCollection#remove(java.lang.Object) 由于remove方法会增加modCount
     * @see AbstractList.Itr#next() 在这里对比modCount会不一致
     *
     */
    public static void foreachRemove(){
        //List<Integer> arrList = Arrays.asList(1, 2, 3, 4, 5, 6, 15, 7, 8, 9, 13, 0, 11);

        for (Integer value : arrList) {
            arrList.remove(value);
        }
    }

    /**
     * 采用普通for循环移除，还是会报异常
     *
     * Exception in thread "main" java.lang.UnsupportedOperationException
     * 	at java.util.AbstractList.remove(AbstractList.java:161)
     * 	at java.util.AbstractList$Itr.remove(AbstractList.java:374)
     * 	at java.util.AbstractCollection.remove(AbstractCollection.java:293)
     * 	at com.eumji.test.collution.ListDemo.forRemove(ListDemo.java:48)
     * 	at com.eumji.test.collution.ListDemo.main(ListDemo.java:56)
     *
     *
     * 	原因是Arrays.asList方法创建的对象是{@link Arrays.ArrayList} 他没有重写remove方法，remove方法默认抛出{@link UnsupportedOperationException}
     *
     * 	所以就算使用iterator的remove以没有办法，必须要重写过 {@link} java.util.AbstractList#remove(int)方法才能进行移除操作
     *
     */
    public static void  forRemove(){
        List<Integer> arrList = Arrays.asList(1, 2, 3, 4, 5, 6, 15, 7, 8, 9, 13, 0, 11);
        System.out.println(arrList);

        for (int i = 0; i < arrList.size(); i++) {
            arrList.remove(arrList.get(i));
        }

        Iterator<Integer> iterator = arrList.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        System.out.println(arrList);
    }

    /**
     * 不报错，但是也无法全部移除
     * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
     * [2, 4, 6, 8, 10]
     *
     * 产生的原因是由于arrayList进行了复写， {@link ArrayList#remove(java.lang.Object)}
     *
     * ps：由于我们这里 使用的是arrList.size()来获取最大值，而remove每次移动后都会改变
     * 所以不会报错，只会移除部分就停止了。并且移除后内部会往前移，所以双数都无法移除
     */
    public static void  arrayListRemove(){

        System.out.println(arrList);
        //由于移除下标变更的问题，部分是没有被移除的...因为size是动态的每次都会获取
        for (int i = 0; i < arrList.size(); i++) {
            arrList.remove(arrList.get(i));
        }
        System.out.println(arrList);
    }

    /**
     * 反向进行移除.没毛病，可以全部移除
     */
    public static void  arrayListReRemove(){

        System.out.println(arrList);
        //由于移除下标变更的问题，部分是没有被移除的...因为size是动态的每次都会获取
        for (int i = arrList.size() - 1; i >= 0; i--) {
            arrList.remove(arrList.get(i));
            //arrList.remove(i);
        }
        System.out.println(arrList);
    }


    /**
     * 为什么这里可以呢？
     * 因为我们调用的是iterator.remove {@link ArrayList.Itr#remove()}
     *
     * public void remove() {
     *             if (lastRet < 0)
     *                 throw new IllegalStateException();
     *             checkForComodification();
     *
     *             try {
     *                 ArrayList.this.remove(lastRet);
     *                 cursor = lastRet;
     *                 lastRet = -1;
     *                 expectedModCount = modCount;
     *             } catch (IndexOutOfBoundsException ex) {
     *                 throw new ConcurrentModificationException();
     *             }
     *         }
     *
     *
     *
     *  简单来说就是因为Iterator的重写方法中，移除操作更新了expectedModCount，让他和list里的modCount保持一致
     *
     */
    public static void commonRemove(){
        //如果使用的是Arrays的asList来创建，则创建的是Arrays.ArrayList(也无法移除)
        //List<Integer> arrList = Arrays.asList(1, 2, 3, 4, 5, 6, 15, 7, 8, 9, 13, 0, 11);
        System.out.println(arrList);
        Iterator<Integer> iterator = arrList.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            iterator.remove();
        }
        System.out.println(arrList);
    }


    public static void main(String[] args) {
        //foreachRemove();
        forRemove();
        //arrayListRemove();
        //arrayListReRemove();
        //commonRemove();
    }
}
