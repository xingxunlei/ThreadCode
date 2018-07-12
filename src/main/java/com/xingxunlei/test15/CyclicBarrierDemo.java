package com.xingxunlei.test15;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier
 * <p>
 * CyclicBarrier是一个同步工具类，允许一组线程互相等待，知道到达某个公共屏障点。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时的互相等待，此时，CyclicBarrier很有用。
 * 因为该barrier在释放等待线程后可以重用，所以称它为循环的barrier。
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点1，当前已有" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程到达。" + (cyclicBarrier.getNumberWaiting() == 2? "全部到达，继续执行..." : "等候..." ));
                        cyclicBarrier.await();

                        System.out.println("----------------------------------------");

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点2，当前已有" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程到达。" + (cyclicBarrier.getNumberWaiting() == 2? "全部到达，继续执行..." : "等候..." ));
                        cyclicBarrier.await();

                        System.out.println("----------------------------------------");

                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("线程：" + Thread.currentThread().getName() + "即将到达集合点3，当前已有" + (cyclicBarrier.getNumberWaiting() + 1) + "个线程到达。" + (cyclicBarrier.getNumberWaiting() == 2? "全部到达，继续执行..." : "等候..." ));
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
