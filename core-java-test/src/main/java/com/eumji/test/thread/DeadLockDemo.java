package com.eumji.test.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *
 * 死锁demo
 *
 * 运行main方法
 * 命令行输入 jps 查看所有java进程
 * 查看栈信息 jstack pid pid指代进程号
 *
 * "pool-1-thread-2":
     at com.eumji.test.thread.DeadLockDemo.lambda$main$1(DeadLockDemo.java:39)
     - waiting to lock <0x00000000d7936b48> (a java.lang.Object)
     - locked <0x00000000d7936b58> (a java.lang.Object)
     at com.eumji.test.thread.DeadLockDemo$$Lambda$2/1452126962.run(Unknown Source)
     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     at java.lang.Thread.run(Thread.java:748)
     "pool-1-thread-1":
     at com.eumji.test.thread.DeadLockDemo.lambda$main$0(DeadLockDemo.java:26)
     - waiting to lock <0x00000000d7936b58> (a java.lang.Object)
     - locked <0x00000000d7936b48> (a java.lang.Object)
     at com.eumji.test.thread.DeadLockDemo$$Lambda$1/1480010240.run(Unknown Source)
     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
     at java.lang.Thread.run(Thread.java:748)

     Found 1 deadlock.



 死锁的原因:

 〈1〉互斥条件。即某个资源在一段时间内只能由一个进程占有，不能同时被两个或两个以上的进程占有。这种独占资源如CD-ROM驱动器，打印机等等，
 必须在占有该资源的进程主动释放它之后，其它进程才能占有该资源。这是由资源本身的属性所决定的。如独木桥就是一种独占资源，两方的人不能同时过桥。

 〈2〉不可抢占条件。进程所获得的资源在未使用完毕之前，资源申请者不能强行地从资源占有者手中夺取资源，而只能由该资源的占有者进程自行释放。
 如过独木桥的人不能强迫对方后退，也不能非法地将对方推下桥，必须是桥上的人自己过桥后空出桥面（即主动释放占有资源），对方的人才能过桥。

 〈3〉占有且申请条件。进程至少已经占有一个资源，但又申请新的资源；由于该资源已被另外进程占有，此时该进程阻塞；但是，它在等待新资源之时，
 仍继续占用已占有的资源。还以过独木桥为例，甲乙两人在桥上相遇。甲走过一段桥面（即占有了一些资源），还需要走其余的桥面（申请新的资源），
 但那部分桥面被乙占有（乙走过一段桥面）。甲过不去，前进不能，又不后退；乙也处于同样的状况。

 〈4〉循环等待条件。存在一个进程等待序列{P1，P2，...，Pn}，其中P1等待P2所占有的某一资源，P2等待P3所占有的某一源，......，
 而Pn等待P1所占有的的某一资源，形成一个进程循环等待环。就像前面的过独木桥问题，甲等待乙占有的桥面，而乙又等待甲占有的桥面，从而彼此循环等待。




 *
 *
 *
 * @email eumji025@gmail.com
 * @author: EumJi
 * @date: 18-1-15
 * @time: 下午10:20
 */
public class DeadLockDemo {
private static final Logger LOGGER = LoggerFactory.getLogger(DeadLockDemo.class);
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        ExecutorService executorService =Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            synchronized (o1) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    LOGGER.warn( "Interrupted!", e);
                }
                synchronized (o2) {
                    System.out.println("我进入锁了");
                }
            }
        });

        executorService.execute( () -> {
            synchronized (o2) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    LOGGER.warn( "Interrupted!", e);
                }
                synchronized (o1) {
                    System.out.println("我进入锁了");
                }
            }
        });
    }
}
