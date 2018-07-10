package com.xingxunlei.test09;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Callable 和 Future的使用
 */
public class CallableAndFuture {

    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        Future<String> future = threadPool.submit(new Callable<String>() {
//            @Override
//            public String call() throws InterruptedException {
//                Thread.sleep(2000);
//                return "hello";
//            }
//        });
//        System.out.println("等待结果...");
//        try {
//            System.out.println("结果为： " + future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        threadPool.shutdown();

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(threadPool);
        for (int i = 0; i < 10; i++) {
            final int task = i;
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return task;
                }
            });
        }
        System.out.println("等待结果返回...");
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
}
