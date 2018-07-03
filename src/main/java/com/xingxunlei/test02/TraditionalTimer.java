package com.xingxunlei.test02;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 传统的定时器
 * 1、Timer是jdk中提供的一个定时器工具，使用的时候会在主线程之外起一个单独的线程执行指定的计划任务，可以指定执行一次或者反复执行多次。
 * 2、TimerTask是一个实现了Runnable接口的抽象类，代表一个可以被Timer执行的任务。
 */
public class TraditionalTimer {

    private static int count = 0;
    public static void main(String[] args) {
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("bombing!");
//            }
//        }, 10000, 3000);

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("bombing!");
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        System.out.println("bombing!!");
//                    }
//                }, 2000);
//            }
//        }, 2000);

        class MyTimerTask extends TimerTask {
            @Override
            public void run() {
                count = (count + 1) % 2;
                System.out.println("bombing!");
                new Timer().schedule(new MyTimerTask(), 2000 + 2000 * count);
            }
        }

        new Timer().schedule(new MyTimerTask(), 2000);

        while (true) {
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
