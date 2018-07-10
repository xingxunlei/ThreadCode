package com.xingxunlei.test11;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁示例
 */
public class ReadWriteLock {

    public static void main(String[] args) {

        final Queue queue = new Queue();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        queue.get();
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        queue.put(new Random().nextInt(10000));
                    }
                }
            }.start();
        }

    }
}

class Queue {
    private Object data = null;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void get() {
        readWriteLock.readLock().lock();
        System.out.println(">>>>>>>>>>>>>>> " + Thread.currentThread().getName() + " be ready to read data.");
        try {
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(">>>>>>>>>>>>>>> " + Thread.currentThread().getName() + " have read data : " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void put(Object data) {
        readWriteLock.writeLock().lock();
        System.out.println("<<<<<<<<<<<<<<< " + Thread.currentThread().getName() + " be ready to write data.");
        try {
            Thread.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println("<<<<<<<<<<<<<<< " + Thread.currentThread().getName() + " have write data : " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
