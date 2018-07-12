package com.xingxunlei.test16;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch
 * <p>
 * CountDownLatch是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * 用给定的计数 初始化 CountDownLatch。由于调用了 countDown() 方法，所以在当前计数到达零之前，await 方法会一直受阻塞。之后，会释放所有等待的线程，await 的所有后续调用都将立即返回。
 * 这种现象只出现一次，计数无法被重置。如果需要重置计数，请考虑使用 CyclicBarrier。
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("线程：" + Thread.currentThread().getName() + "等待命令中...");
                        cdOrder.await();

                        System.out.println("线程：" + Thread.currentThread().getName() + "已接受命令并开始执行...");

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "回复命令执行结果...");
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }

        try {
            Thread.sleep((long) (Math.random() * 10000));
            System.out.println("线程：" + Thread.currentThread().getName() + "即将发布命令...");

            cdOrder.countDown();
            System.out.println("线程：" + Thread.currentThread().getName() + "已发布命令，等待结果返回...");
            cdAnswer.await();

            System.out.println("线程：" + Thread.currentThread().getName() + "已接受返回结果。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
