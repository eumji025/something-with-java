package com.eumji.test.thread;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 *   阻塞队列名称         10个线程 - 1000000    20个线程500000      内存
 * LinkedBlockingQueue      7213-9000          7000 - 8000    530M左右 基于链表
 *
 * ArrayBlockingQueue       4500 - 5200        3800 - 5000    270M左右 但是设置的容量只有11000000 基于数组
 *
 * PriorityBlockingQueue    4800-5000          4800 - 6000     300M左右 容量也是11000000 基于数组
 *
 * ConcurrentLinkedQueue    6500-7200          6500 - 7000      500M左右 基于链表
 *
 * LinkedTransferQueue      6200-8000          5000 - 6000      600M左右 基于链表
 *
 *
 *
 * 总结：
 * 说实话结果有点出乎我所料，居然ArrayBlockingQueue的并发put的效率这么高
 * 不过我也看了一下ArrayBlockingQueue和LinkedBlockingQueue的代码，感觉也很类似，不同的地方在于LinkedBlockingQueue需要将插入的元素添加额外的内存
 *
 * 事实证明在我们明确最大的容量情况下，或许使用基于数组的阻塞队列会节省空间，并有更高的插入效率
 *
 *
 * 待验证 take 和 put 同时进行的情况
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-3-26
 * @time: 下午8:50
 */
public class BLockingQueueDemo {

    public static void main(String[] args) throws InterruptedException, IOException {
        //ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue<Long>();
        BlockingQueue<Long> queue = new LinkedTransferQueue();
        CountDownLatch countDownLatch = new CountDownLatch(20);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 500000; j++) {

                    queue.offer(System.currentTimeMillis()+new Random().nextInt(1000000));
                }

                countDownLatch.countDown();

            }).start();
        }

        countDownLatch.await();

        System.out.println("cost time:" +(System.currentTimeMillis()-begin));
        System.out.println(queue.size());

        System.in.read();

    }
}
