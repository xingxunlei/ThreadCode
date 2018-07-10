package com.xingxunlei.test10;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 传统的线程锁
 * <p>
 * Lock比传统线程模型中的synchronized方式更加面向对象，与生活中的锁类似，锁本身也是一个对象。
 * 两个线程执行的代码片段要实现同步互斥的效果，它们必须用同一个Lock对象。
 * 锁是在代表要操作的资源的类的内部方法中，而不是线程代码中。
 */
public class TraditionalThreadLock {

    public static void main(String[] args) {
        new TraditionalThreadLock().init();
    }

    private void init() {
        final OutPuter outPuter = new OutPuter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outPuter.output2("zhangxiaoqiang");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outPuter.output2("wangxiaowu");
                }
            }
        }).start();
    }

    /**
     * 内部类
     */
    static class OutPuter {

        Lock lock = new ReentrantLock();

        /**
         * 未加锁，非线程安全
         *
         * @param s
         */
        public void output(String s) {
            int n = s.length();
            for (int i = 0; i < n; i++) {
                System.out.print(s.charAt(i));
            }
            System.out.println();
        }

        /**
         * 加锁，达到互斥效果
         * <p>
         * 在使用lock的时候，将代码块用 try/finally 包裹，在finally中释放锁对象。
         *
         * @param s
         */
        public void output2(String s) {
            int n = s.length();
            lock.lock();
            try {
                for (int i = 0; i < n; i++) {
                    System.out.print(s.charAt(i));
                }
                System.out.println();
            } finally {
                lock.unlock();
            }
        }
    }

}
