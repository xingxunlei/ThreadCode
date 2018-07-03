package com.xingxunlei.test01;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 传统线程的创建方式
 * <p>
 * 实现Runnable和实现Callable接口的方式基本相同，不过是后者执行call()方法有返回值，后者线程执行体run()方法无返回值，因此可以把这两种方式归为一种这种方式与继承Thread类的方法之间的差别如下：
 * 1、线程只是实现Runnable或实现Callable接口，还可以继承其他类。
 * 2、这种方式下，多个线程可以共享一个target对象，非常适合多线程处理同一份资源的情形。
 * 3、但是编程稍微复杂，如果需要访问当前线程，必须调用Thread.currentThread()方法。
 * 4、继承Thread类的线程类不能再继承其他父类（Java单继承决定）。
 */
public class TraditionalThread {
    public static void main(String[] args) {
        /**
         * 方式1： 继承Thread类，覆盖run方法
         * 1).定义Thread类的子类，并重写该类的run方法，该run方法的方法体就代表了线程要完成的任务。因此把run()方法称为执行体。
         * 2).创建Thread子类的实例，即创建了线程对象。
         * 3).调用线程对象的start()方法来启动该线程。
         */
        MyThread thread1 = new MyThread();
        thread1.start();

        /**
         * 方式2-1：实现Runnable接口
         * 1).定义runnable接口的实现类，并重写该接口的run()方法，该run()方法的方法体同样是该线程的线程执行体。
         * 2).创建 Runnable实现类的实例，并依此实例作为Thread的target来创建Thread对象，该Thread对象才是真正的线程对象。
         * 3).调用线程对象的start()方法来启动该线程。
         */
        Thread thread2 = new Thread(new RunnableThread());
        thread2.start();

        /**
         * 方式2-2：通过Callable和Future创建线程
         * 1).创建Callable接口的实现类，并实现call()方法，该call()方法将作为线程执行体，并且有返回值。
         * 2).创建Callable实现类的实例，使用FutureTask类来包装Callable对象，该FutureTask对象封装了该Callable对象的call()方法的返回值。
         * 3).使用FutureTask对象作为Thread对象的target创建并启动新线程。
         * 4).调用FutureTask对象的get()方法来获得子线程执行结束后的返回值。
         */
        CallableThread callableThread = new CallableThread();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(callableThread);
        Thread thread3 = new Thread(futureTask);
        thread3.start();
        try {
            System.out.println("thread3的返回值：" + futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 猜测下面代码会执行哪个run方法？？？为什么？？？
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("runnable :" + Thread.currentThread().getName());
                }
            }
        }) {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread :" + Thread.currentThread().getName());
                }
            }
        }.start();

    }
}

class MyThread extends Thread {
    private int i;

    @Override
    public void run() {
        for (i = 0; i < 100; i++) {
            System.out.println("1:" + Thread.currentThread().getName() + " " + i);
        }
    }
}

class RunnableThread implements Runnable {
    private int i;

    @Override
    public void run() {
        for (i = 0; i < 100; i++) {
            System.out.println("2:" + Thread.currentThread().getName() + " " + i);
        }
    }
}

class CallableThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 100; i++) {
            System.out.println("3:" + Thread.currentThread().getName() + " " + i);
        }
        return i;
    }
}
