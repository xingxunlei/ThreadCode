package com.xingxunlei.test14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore
 *
 * 信号量示例
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程：" + Thread.currentThread().getName() + "进入，当前已有" + (3 - semaphore.availablePermits()) + "个并发。");

                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("线程：" + Thread.currentThread().getName() + "即将离开。");
                    semaphore.release();

                    // 这句输出语句没有跟上面的代码合成原子单元，所以输出可能不准确
                    System.out.println("线程：" + Thread.currentThread().getName() + "已离开，当前已有" + (3 - semaphore.availablePermits()) + "个并发。");
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
