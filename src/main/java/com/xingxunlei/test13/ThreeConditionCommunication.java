package com.xingxunlei.test13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition
 * <p>
 * 一个锁内部可以有多个condition，即有多路等待和通知。
 * 在传统的Object监视器上只能有一路等待和通知，要实现多路等待和通知，必须嵌套使用多个同步监视器对象。
 */
public class ThreeConditionCommunication {

    public static void main(String[] args) {

        final Business business = new Business();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            business.sub2(i);
                        }
                    }
                }
        ).start();

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 50; i++) {
                            business.sub3(i);
                        }
                    }
                }
        ).start();

        for (int i = 0; i < 50; i++) {
            business.sub1(i);
        }
    }

    static class Business {
        private Lock lock = new ReentrantLock();
        private Condition condition1 = lock.newCondition();
        private Condition condition2 = lock.newCondition();
        private Condition condition3 = lock.newCondition();
        private int shouldSub = 1;

        public void sub1(int i) {
            lock.lock();
            try {
                while (shouldSub != 1) {
                    try {
                        condition1.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 10; j++) {
                    System.out.println("sub1 thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                shouldSub = 2;
                condition2.signal();
            } finally {
                lock.unlock();
            }
        }

        public void sub2(int i) {
            lock.lock();
            try {
                while (shouldSub != 2) {
                    try {
                        condition3.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 20; j++) {
                    System.out.println("sub2 thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                shouldSub = 3;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }

        public void sub3(int i) {
            lock.lock();
            try {
                while (shouldSub != 3) {
                    try {
                        condition3.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 100; j++) {
                    System.out.println("sub3 thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                shouldSub = 1;
                condition3.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
