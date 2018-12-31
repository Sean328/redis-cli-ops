package com.xxl.redis.lock;

/**
 * @author lixin
 * @date 2018-12-27 15:00
 **/
public interface JedisLockService {

    /**
     * 获取锁，使用默认时间
     * @param name
     * @return
     */
    Boolean lock(String name);

    /**
     * 获取锁，指定锁存在的时间
     * @param name 锁的key
     * @param msec 毫秒
     * @return
     */
    Boolean lock(String name, Long msec);

    /**
     * 尝试获取锁，默认3次重试
     * @param name 锁的key
     * @param retryInteval 未获取到锁时重试的间隔
     * @return
     */
    Boolean tryLock(String name, Integer retryInteval);

    /**
     * 尝试获取锁并指定锁存在时间
     * @param name
     * @param msec 毫秒
     * @param retryInteval 未获取到锁时重试的间隔
     * @return
     */
    Boolean tryLock(String name, Long msec, Integer retryInteval);

    /**
     * 释放锁
     * @return
     */
    Boolean unlock();

}
