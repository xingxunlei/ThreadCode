package com.xingxunlei.test12;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用读写锁实现自定义cache
 * @author xingxunlei
 */
public class CustomCache {

    /**
     * 定义一个存放数据的map
     */
    private Map<String, Object> cache = new HashMap<>();
    /**
     * 定义读写锁
     */
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 从缓存中查询数据
     * @param key 需要查询的key
     * @return
     */
    public Object get(String key) {
        // 上读锁
        readWriteLock.readLock().lock();
        Object value = null;
        try {
            // 从map中取数据
            value = cache.get(key);
            // 判断缓存中是否存在数据
            if (null == value) {
                // 缓存中不存在
                // 释放读锁
                readWriteLock.readLock().unlock();
                // 上写锁
                readWriteLock.writeLock().lock();
                try {
                    // 二次判断缓存中是否存在数据, 防止其它线程串改数据
                    value = cache.get(key);
                    if (null == value) {
                        // 数据确实不存在, 从数据库中查询数据并放入缓存map
                        value = queryFromDB(key);
                        cache.put(key, value);
                    }
                } finally {
                    // 释放写锁
                    readWriteLock.writeLock().unlock();
                }
                // 上读锁
                readWriteLock.readLock().lock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            readWriteLock.readLock().unlock();
        }
        // 返回数据
        return value;
    }

    private Object queryFromDB(String key) {
        return "sample from db which key is : " + key;
    }

    public static void main(String[] args) {

    }
}
