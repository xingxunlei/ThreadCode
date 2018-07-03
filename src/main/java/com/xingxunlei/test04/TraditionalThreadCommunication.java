package com.xingxunlei.test04;

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
