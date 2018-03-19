package com.eumji.test.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 *
 * 一个简单的forkjoin示例
 * 当不满足条件时。平均切分，继续尝试
 * 满足则计算
 * 最终归并结果
 *
 *  后续会分析一波forkJoin的实现 - 已经拖了很久了
 * @author eumji
 * @date 2018/1/17
 */
public class ForkJoinDemo {

    static class CountTask extends RecursiveTask<Long> {

        private static int THRESHOLD = 1000;
        private int begin;
        private int end;

        public CountTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            int size;
            if ((size = end-begin) < THRESHOLD){
                for (int i = begin; i <= end; i++) {
                    sum += i;
                }
            }else {
                CountTask left = new CountTask(begin, begin + size / 2);
                left.fork();
                CountTask right = new CountTask(begin + size / 2 + 1, end);
                right.fork();
                sum = left.join() + right.join();
                //                ArrayList<CountTask> countTasks = new ArrayList<>(size);
//                for (int j = 0; j < size; j++) {
//                    CountTask task = new CountTask(begin, THRESHOLD);
//                    countTasks.add(task);
//                    task.fork();
//                    begin += THRESHOLD;
//                }
//
//                for (CountTask countTask : countTasks) {
//                    try {
//                        sum += countTask.get();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            return sum;
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(0, 20000000);
        long begin = System.currentTimeMillis();
        ForkJoinTask<Long> submit = forkJoinPool.submit(countTask);
        System.out.println(submit.get());
        long end = System.currentTimeMillis();
        System.out.println("forkjoin used: "+(end-begin));
//        long sum = 0;
//        for (int i = 0; i <= 20000000; i++) {
//            sum += i;
//        }
//        System.out.println(sum);
//        long end2 = System.currentTimeMillis();
//        System.out.println("foreach used: "+(end2-end));
    }
}

