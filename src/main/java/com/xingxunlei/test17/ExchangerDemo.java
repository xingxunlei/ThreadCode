package com.xingxunlei.test17;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger<V>
 * @param V - 可以交换的对象类型
 * <p>
 * Exchanger用于进行线程间的数据交换，是一个用于线程间协作的工具类。
 * 它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。这两个线程通过exchange方法交换数据，如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange方法。当两个线程都到达同步点时，这两个线程就可以交换数据，将笨线程生产出来的数据传递给对方。
 * 因此，使用exchanger的重点是成对的线程使用exchange方法，当有一堆线程达到了同步点，就会进行数据交换。
 */
public class ExchangerDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data1 = "xingxunlei";
                    System.out.println("线程：" + Thread.currentThread().getName() + " 正在把数据【" + data1 + "】交换出去。");

                    Thread.sleep((long) (Math.random() * 10000));

                    String data2 = exchanger.exchange(data1);
                    System.out.println("线程：" + Thread.currentThread().getName() + " 换回数据【" + data1 + "】。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data1 = "www.zzylm.com";
                    System.out.println("线程：" + Thread.currentThread().getName() + " 正在把数据【" + data1 + "】交换出去。");

                    Thread.sleep((long) (Math.random() * 10000));

                    String data2 = exchanger.exchange(data1);
                    System.out.println("线程：" + Thread.currentThread().getName() + " 换回数据【" + data1 + "】。");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.shutdown();
    }
}

