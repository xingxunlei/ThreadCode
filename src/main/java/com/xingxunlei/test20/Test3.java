package com.xingxunlei.test20;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test3 extends Thread {
    private ToDo todo;
    private String key;
    private String value;

    public Test3(String key1, String key2, String value) {
        this.todo = ToDo.getInstance();
        /*常量“1”和“1”是同一个对象，下面这行代码就是要用"1" + "" 的方式产生新的对象，以实现内容没有改变，仍然相等（都还为“1”），但对象却不再是同一个的效果*/
        this.key = key1 + key2;
        this.value = value;
    }

    @Override
    public void run() {
        todo.doSome(key, value);
    }

    public static void main(String[] args) {
        Test3 t1 = new Test3("1", "", "1");
        Test3 t2 = new Test3("1", "", "2");
        Test3 t3 = new Test3("3", "", "3");
        Test3 t4 = new Test3("4", "", "4");

        System.out.println("begin:" + (System.currentTimeMillis() / 1000));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}

class ToDo {
    private ToDo() {
    }

    private static ToDo instance = new ToDo();

    public static ToDo getInstance() {
        return instance;
    }

    private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();

    public void doSome(Object key, String value) {
        Object o = key;
        if (!keys.contains(o)) {
            keys.add(o);
        } else {
            for (Iterator iterator = keys.iterator(); iterator.hasNext(); ) {
                Object oo = iterator.next();
                if (o.equals(oo)) {
                    o = oo;
                }
            }
        }
        synchronized (o) {
            try {
                Thread.sleep(1000);
                System.out.println("【key:" + key + "; value:" + value + "】" + System.currentTimeMillis() / 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
