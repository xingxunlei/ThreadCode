package com.xingxunlei.test03;

/**
 * 传统的线程安全问题
 *
 * 1).无论synchronized关键字加在方法上还是对象上，如果它作用的对象是非静态的，则它取得的锁是对象；如果synchronized作用的对象是一个静态方法或一个类，则它取得的锁是对类，该类所有的对象同一把锁。
 * 2).每个对象只有一个锁（lock）与之相关联，谁拿到这个锁谁就可以运行它所控制的那段代码。
 * 3).实现同步是要很大的系统开销作为代价的，甚至可能造成死锁，所以尽量避免无谓的同步控制。比如，同步方法内再次同步代码块，可能会造成死锁。
 *
 */
public class TraditionalThreadSynchronized {

    public static void main(String[] args) {
        new TraditionalThreadSynchronized().init();
    }

    private void init() {
        final OutPuter outPuter = new OutPuter();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outPuter.output("zhangsannnnnnnnnnnnnn");
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outPuter.output("lisisssssssssss");
                }
            }
        }).start();
    }

    /**
     * 内部类
     */
    static class OutPuter {

        /**
         * 非线程安全
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
         * 非线程安全
         *
         * 因为synchronized后边同步的并非同一个对象
         * @param s
         */
        public void output2(String s) {
            int n = s.length();
            synchronized (s) {
                for (int i = 0; i < n; i++) {
                    System.out.print(s.charAt(i));
                }
                System.out.println();
            }
        }

        /**
         * 可以达到线程同步的效果
         * @param s
         */
        public void output3(String s) {
            int n = s.length();
            /**
             * 要达到线程同步的效果，synchronized 必须为同一个对象锁
             */
            synchronized (this) {
                for (int i = 0; i < n; i++) {
                    System.out.print(s.charAt(i));
                }
                System.out.println();
            }
        }

        /**
         * 可以达到线程同步的效果
         *
         * 但是 在使用synchronized 的时候，尽可能的同步少量的代码块，而非同步整个method
         * @param s
         */
        public synchronized void output4(String s) {
            int n = s.length();
            for (int i = 0; i < n; i++) {
                System.out.print(s.charAt(i));
            }
            System.out.println();
        }

        /**
         * 可以达到线程同步的效果
         * @param s
         */
        public void output5(String s) {
            int n = s.length();
            /**
             * 要达到线程同步的效果，synchronized 必须为同一个对象锁
             */
            synchronized (OutPuter.class) {
                for (int i = 0; i < n; i++) {
                    System.out.print(s.charAt(i));
                }
                System.out.println();
            }
        }

        /**
         * 可以达到线程同步的效果
         * @param s
         */
        public static synchronized void output6(String s) {
            int n = s.length();
            for (int i = 0; i < n; i++) {
                System.out.print(s.charAt(i));
            }
            System.out.println();
        }
    }

}
