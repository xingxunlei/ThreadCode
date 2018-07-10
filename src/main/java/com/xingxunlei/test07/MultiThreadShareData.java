package com.xingxunlei.test07;

/**
 * 多个线程之间共享数据
 * <p>
 * 如果每个线程执行的代码相同，可以使用同一个Runnable对象，这个Runnable对象中存在共享数据。
 * 如果每个线程执行的代码不同，这时候需要使用不同的Runnable对象：
 * 1).将共享数据封装在另外一个对象中，然后将这个对象逐一传递给各个Runnable对象。每个线程对共享数据的操作方法也分配到那个对象身上去完成，这样容易实现针对该数据进行的各个操作的互斥和通信。
 * 2).将这些Runnable对象作为某一个类中的内部类，共享数据作为这个外部类中的成员变量，每个线程对共享数据的操作方法也分配给外部类，以便实现对共享数据进行的各个操作的互斥和通信，作为内部类的各个Runnable对象调用外部类的这些方法。
 * 3).将上面两种方式结合：将共享数据封装在另外一个对象中，每个线程对共享数据的操作方法也分配到那个对象身上去完成，对象作为这个外部类中的成员变量或方法中的局部变量，每个线程的Runnable对象作为外部类中的成员内部类或局部内部类。
 * 4).总之，要同步互斥的局端代码最好是分别放在几个独立的方法中，这些方法再放在同一个类中，这样比较容易实现它们之间的同步互斥和通信。
 */
public class MultiThreadShareData {


    public static void main(String[] args) {
//        example1();
//        example2();
        example3();
    }

    /**
     * 示例1
     */
    public static void example1() {
        ShareData data = new ShareData();
        new Thread(new IncrementRunnable(data)).start();
        new Thread(new DecrementRunnable(data)).start();
    }

    static class IncrementRunnable implements Runnable {
        private ShareData data;

        IncrementRunnable(ShareData data) {
            this.data = data;
        }

        @Override
        public void run() {
            data.increment();
        }
    }

    static class DecrementRunnable implements Runnable {
        private ShareData data;

        DecrementRunnable(ShareData data) {
            this.data = data;
        }

        @Override
        public void run() {
            data.decrement();
        }
    }

    static class ShareData {

        private int i;

        public void increment() {
            i++;
            System.out.println("执行了 ++ 操作。。。");
        }

        public void decrement() {
            i--;
            System.out.println("执行了 -- 操作。。。");
        }
    }

    /**
     * 示例2
     */
    public static void example2() {
        final ShareData data = new ShareData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data.increment();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data.decrement();
            }
        }).start();
    }

    /**
     * 示例3
     * <p>
     * 设计4个线程，其中2个线程每次对j+1，另外2个线程每次对j-1。
     */
    public static void example3() {
        ThreadTest thread = new ThreadTest();
        ThreadTest.Inc inc = thread.new Inc();
        ThreadTest.Dec dec = thread.new Dec();
        for (int i = 0; i < 2; i++) {
            new Thread(inc).start();
            new Thread(dec).start();
        }
    }

}

class ThreadTest {
    private int j;

    public synchronized void inc() {
        j++;
        System.out.println(Thread.currentThread().getName() + "-inc:" + j);
    }

    public synchronized void dec() {
        j--;
        System.out.println(Thread.currentThread().getName() + "-dec:" + j);
    }

    class Inc implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                inc();
            }
        }
    }

    class Dec implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                dec();
            }
        }
    }
}