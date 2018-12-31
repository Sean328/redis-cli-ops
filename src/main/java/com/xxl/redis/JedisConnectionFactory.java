package com.xxl.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author sean
 * @date 2018/12/09/1:49
 **/
public class JedisConnectionFactory extends Jedis {

    protected JedisPool jedisPool;
    protected String hostName;
    protected String port;
    protected String password;

    public JedisConnectionFactory(String hostName, String port, String password) {
        this.hostName = hostName;
        this.port = port;
        this.password = password;

        int a = Integer.valueOf(port);

        jedisPool = new JedisPool(new GenericObjectPoolConfig(),hostName, a);

    }

    public JedisConnectionFactory() {

    }

    public void init(){
//        this.hostName = hostName;
//        this.port = port;
//        this.password = password;

        jedisPool = new JedisPool(new GenericObjectPoolConfig(),hostName, Integer.valueOf(port));
    }


    public String getHostName() {
        return hostName;
    }

    public JedisConnectionFactory setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPort() {
        return port;
    }

    public JedisConnectionFactory setPort(String port) {
        this.port = port;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public JedisConnectionFactory setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
