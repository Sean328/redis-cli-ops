package com.xxl.redis.lock;

/**
 * @author sean
 * @date 2018/12/09/23:32
 **/
public interface SpringDataRedisLockService {
     boolean lock(String key);

     boolean lock(String key, long lockMillis);

     boolean tryLock(String key);

     boolean tryLockWithLockTime(String key, long lockMillis);

     boolean tryLock(String key, long intervalMillis);

     boolean tryLockWithLockTime(String key, long lockMillis, long intervalMillis);

     void unLock(String key);
}
