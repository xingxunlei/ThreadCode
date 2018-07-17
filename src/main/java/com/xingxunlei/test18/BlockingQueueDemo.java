package com.xingxunlei.test18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 队列：一种特殊的线性表，只允许在表的前端进行删除操作，在表的后端进行添加操作。一般分为阻塞队列和非阻塞队列。
 *
 * 阻塞队列：支持阻塞操作的队列。具体来讲，支持阻塞添加和阻塞移除。实现了阻塞接口（BlockingQueue）的队列。
 * 阻塞队列和Semaphore类似，但是，阻塞队列通常是一方放数据，另一方取数据。而Semaphore是同一方设置和释放信号量。
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        final BlockingQueue queue = new ArrayBlockingQueue(3);
        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName() + ": 准备放数据！>>>>>>>>>>");

                            queue.put(1);
                            System.out.println(Thread.currentThread().getName() + ": 已经放入数据！队列目前有【" + queue.size() + "】个数据。");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + ": 准备取数据！<<<<<<<<<<");

                        queue.take();
                        System.out.println(Thread.currentThread().getName() + ": 已经取出数据！队列目前有【" + queue.size() + "】个数据。");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
