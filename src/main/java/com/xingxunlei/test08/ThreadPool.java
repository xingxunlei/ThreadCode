package com.xingxunlei.test08;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池
 * <p>
 * 线程池的作用就是限制系统中执行线程的数量。
 * 1).使用线程池可以减少创建和销毁线程的次数，每个工作线程都可以被重负利用，可执行多个任务。
 * 2).使用线程池的时候要根据系统的承受能力，调整线程池中工作线程的数目，防止系统过载。
 * <p>
 * 常见的线程池有：
 * 1).newSingleThreadExecutor
 * 创建一个单线程的线程池。这个线程池中只有一个线程在工作，也就是相当于单线程串行执行所有任务。如果这个唯一的线程因为异常结束，那么会有一个新的线程来代替他。次线程池保证所有任务的执行顺序按照任务的提交顺序执行。
 * <p>
 * 2).newFixedThreadPoll
 * 创建固定大小的线程池。每次提交一个任务就创建一个线程，知道线程数达到线程池的最大数目。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新的线程。
 * <p>
 * 3).newCachedThreadPool
 * 创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回首部分空闲的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（JVM）能够创建的最大线程数。
 * <p>
 * 4).newScheduledThreadPool
 * 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
 *
 * 注意：
 * 在创建线程池的时候尽可能的使用ThreadPoolExecutor的方式手动创建线程池,更好的规避资源耗尽的风险。
 * newSingleThreadExecutor和newFixedThreadPoll主要的问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM
 * newCachedThreadPool和newScheduledThreadPool主要的问题是线程数最大数是Integer.MAX_VALUE，可能会创建非常多的线程，占用系统资源，甚至OOM
 */
public class ThreadPool {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        for (int i = 0; i < 10; i++) {
//            final int task = i;
//            threadPool.execute(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 10; j++) {
//                        try {
//                            Thread.sleep(20);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + task);
//                    }
//                }
//            });
//        }
//        System.out.println("all of 10 tasks has committed!");
//        threadPool.shutdown(); //所有工作线程结束后关闭
//        threadPool.shutdownNow(); //立刻关闭

//        Executors.newScheduledThreadPool(3).schedule(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ahahahahah!");
//            }
//        }, 6, TimeUnit.SECONDS);

//        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ahahahahah!");
//            }
//        }, 6, 2, TimeUnit.SECONDS);

        /**
         * 注意workqueue的区别以及RejectedExecutionHandler
         */
        ExecutorService pool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2, 50, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactoryBuilder().setNameFormat("executor-default-pool-%d").build(), new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            final int task = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + task);
                    }
                }
            });
        }
        pool.shutdown();
    }
}
