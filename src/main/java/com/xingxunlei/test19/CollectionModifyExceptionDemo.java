package com.xingxunlei.test19;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 集合操作异常示例
 */
public class CollectionModifyExceptionDemo {

//    // 下面代码会抛异常
//    public static void main(String[] args) {
//        List users = new ArrayList<>();
//        users.add(new User("张三", 28));
//        users.add(new User("李四", 25));
//        users.add(new User("王五", 31));
//        users.add(new User("麻六", 31));
//
//        Iterator iterator = users.iterator();
//        while (iterator.hasNext()) {
//            User user = (User) iterator.next();
//            if ("王五".equals(user.getName())) {
//                users.remove(user);
//            }else {
//                System.out.println(user);
//            }
//
//        }
//    }

    // 讲普通list改为线程安全的list，即可解决上面的错误
    public static void main(String[] args) {
        List users = new CopyOnWriteArrayList();
        users.add(new User("张三", 28));
        users.add(new User("李四", 25));
        users.add(new User("王五", 31));
        users.add(new User("麻六", 31));

        Iterator iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = (User) iterator.next();
            if ("王五".equals(user.getName())) {
                users.remove(user);
            }else {
                System.out.println(user);
            }

        }
    }
}
