package com.xxl;

import com.xxl.redis.JedisClusterConnFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @author lixin
 * @date 2018-12-28 15:31
 **/
public class JedisClusterCli extends JedisClusterConnFactory {
    private static final Logger logger = LoggerFactory.getLogger(JedisClusterCli.class);

    @Override
    public void init() {
        super.init();
    }

    @Override
    public String get(String key) {
        Jedis jedis = getReader(key);
        String ret = jedis.get(key);
        jedis.close();

        return ret;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = getWriter(key);
        String ret = jedis.set(key, value);
        jedis.close();

        return ret;
    }

}
