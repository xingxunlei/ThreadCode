package com.xingxunlei.test13;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * condition
 * <p>
 * condition是用来替代传统的Object的wait\notify实现线程间的协作，相比使用Object的wait\notify而言，使用condition的await\signal这种方式实现线程间的协作更加安全、高效。
 * 因此，通常来说推荐使用condition，阻塞队列实际上是使用了condition来模拟线程间协作。
 * 1).Condition是个接口，基本方法就是await()、signal()
 * 2).Condition依赖于Lock接口，生成一个condition的基本代码是lock.newCondition()
 * 3).调用condition的await()、signal()方法，都必须在lock保护之内，就是说要在lock.lock() 和 lock.unlock()之间。
 * 4).condition中的await()对应object中的wait(); condition.signal()对应object.notify(); condition.signalAll()对应object.notifyAll()
 * <p>
 * 在等待 Condition 时，允许发生“虚假唤醒”，这通常作为对基础平台语义的让步。对于大多数应用程序，这带来的实际影响很小，因为 Condition 应该总是在一个循环中被等待，并测试正被等待的状态声明。某个实现可以随意移除可能的虚假唤醒，但建议应用程序程序员总是假定这些虚假唤醒可能发生，因此总是在一个循环中等待。
 */
public class ConditionCommunication {

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

    static class Business {
        private Lock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        private boolean bShouldSub = true;

        public void sub(int i) {
            lock.lock();
            try {
                while (!bShouldSub) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 10; j++) {
                    System.out.println("sub thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                bShouldSub = false;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }

        public void main(int i) {
            lock.lock();
            try {
                while (bShouldSub) {
                    try {
                        condition.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 0; j < 100; j++) {
                    System.out.println("main thread sequece of " + (j + 1) + ", loop of " + (i + 1));
                }
                bShouldSub = true;
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }

}
