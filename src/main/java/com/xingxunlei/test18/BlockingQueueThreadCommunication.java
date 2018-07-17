package com.xingxunlei.test18;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现线程通信
 * <p>
 * 子线程循环10次，接着主线程循环100次，然后回到子线程循环10次，接着再回到主线程循环100次。如此往复50次。
 */
public class BlockingQueueThreadCommunication {

    public static void main(String[] args) {

        final Business business = new Business();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            business.sub(i);
                        }
                    }
                }
        ).start();

        for (int i = 0; i < 50; i++) {
            business.main(i);
        }
    }

    /**
     * 业务类
     * <p>
     * 要用到共同数据（包括同步锁）的若干个方法应归集到同一个类中，这种设计正好体现高内聚的思想。
     */
    static class Business {
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<>(1);

        /**
         * 匿名构造方法，填满队列2
         */
        {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sub(int i) {
            try {
                queue1.put(1);
                for (int j = 0; j < 10; j++) {
                    System.out.println("sub thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void main(int i) {
            try {
                queue2.put(1);
                for (int j = 0; j < 100; j++) {
                    System.out.println("main thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}