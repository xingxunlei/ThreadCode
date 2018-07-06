package com.xingxunlei.test06;

import lombok.Data;
import lombok.ToString;

import java.util.Random;

/**
 * ThreadLocal示例
 * <p>
 * 每个线程调用全局ThreadLocal对象的set方法，相当于往ThreadLocal内部的mao中增加一条记录，key是当前线程，value是set方法传入的值。
 * 在线程结束的时候，可以调用ThreadLocal.clear()方法，会更快的释放内存，当然不跳用也可以，线程结束后会自动释放相关的ThreadLocal变量。
 * <p>
 * 一个ThreadLocal代表一个变量，故而只能存储一个数据。若需要存储多个数据，可将数据封装为对象存储到ThreadLocal中。
 * 在封装对象的时候尽可能的按照单例的思想把ThreadLocal封装到对象中去，便于其它地方使用。
 */
public class ThreadLocalSample {

    static ThreadLocal<Integer> x = new ThreadLocal();
    static ThreadLocal<MyThreadScopeData> myThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

//        for (int i = 0; i < 2; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                    int data = new Random().nextInt();
//                    x.set(data);
//                    System.out.println(Thread.currentThread().getName() + " has put data :" + data);
//                    new A().get();
//                    new B().get();
//
//                }
//            }).start();
//        }

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int data = new Random().nextInt();
                    MyThreadScopeData.getThreadInstance().setName("name" + data);
                    MyThreadScopeData.getThreadInstance().setAge(data);
                    myThreadLocal.set(MyThreadScopeData.getThreadInstance());
                    System.out.println(Thread.currentThread().getName() + " has put data : " + MyThreadScopeData.getThreadInstance());
                    new A().getThreadLocal();
                    new B().getThreadLocal();

                }
            }).start();
        }

    }

    static class A {

        public void get() {
            System.out.println("A from " + Thread.currentThread().getName() + " get data : " + x.get());
        }

        public void getThreadLocal() {
            System.out.println("A from " + Thread.currentThread().getName() + " get threadLocalData : " + MyThreadScopeData.getThreadInstance());
        }

    }

    static class B {

        public void get() {
            System.out.println("B from " + Thread.currentThread().getName() + " get data : " + x.get());
        }

        public void getThreadLocal() {
            System.out.println("B from " + Thread.currentThread().getName() + " get threadLocalData : " + MyThreadScopeData.getThreadInstance());
        }

    }
}

@Data
@ToString
class MyThreadScopeData {
    private MyThreadScopeData() {
    }

    private static ThreadLocal<MyThreadScopeData> threadLocal = new ThreadLocal<>();

    private String name;
    private int age;

    public static MyThreadScopeData getThreadInstance() {
        MyThreadScopeData instance = threadLocal.get();
        if (instance == null) {
            instance = new MyThreadScopeData();
            threadLocal.set(instance);
        }
        return instance;
    }
}
