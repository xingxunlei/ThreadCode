package com.xingxunlei.test04;

/**
 * 线程通信
 * <p>
 * 子线程循环10次，接着主线程循环100次，然后回到子线程循环10次，接着再回到主线程循环100次。如此往复50次。
 * <p>
 * wait、sleep区别：
 */
public class TraditionalThreadCommunication {

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
}

/**
 * 业务类
 * <p>
 * 要用到共同数据（包括同步锁）的若干个方法应归集到同一个类中，这种设计正好体现高内聚的思想。
 */
class Business {
    private boolean bShouldSub = true;

    public synchronized void sub(int i) {
        while (!bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 10; j++) {
            System.out.println("sub thread sequece of " + (j + 1) + ", loop of " + (i + 1));
        }
        bShouldSub = false;
        this.notify();
    }

    public synchronized void main(int i) {
        while (bShouldSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 100; j++) {
            System.out.println("main thread sequece of " + (j + 1) + ", loop of " + (i + 1));
        }
        bShouldSub = true;
        this.notify();
    }
}
