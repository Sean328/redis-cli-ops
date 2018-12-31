package com.xxl.redis.lock;

import com.sean.redis.JedisConnectionFactory;

import javax.annotation.Resource;

/**
 * @author lixin
 * @date 2018-12-27 15:16
 **/
public class JedisLockServiceImpl implements JedisLockService{

    @Resource
    private JedisConnectionFactory jedisConnectionFactory;

    @Override
    public Boolean lock(String name) {
        return null;
    }

    @Override
    public Boolean lock(String name, Long msec) {
        return null;
    }

    @Override
    public Boolean tryLock(String name, Integer retryInteval) {
        return null;
    }

    @Override
    public Boolean tryLock(String name, Long msec, Integer retryInteval) {
        return null;
    }

    @Override
    public Boolean unlock() {
        return null;
    }
}
