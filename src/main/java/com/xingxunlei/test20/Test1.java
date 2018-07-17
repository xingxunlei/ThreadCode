package com.xingxunlei.test20;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Test1 {

    public static void main(String[] args) {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String log = queue.take();
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        for (int i = 0; i < 16; i++) {
            String log = (i < 9 ? "0" : "") + (i + 1);
            try {
                queue.put(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
