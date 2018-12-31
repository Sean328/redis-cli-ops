package com.xxl.redis;

import com.sean.redis.strategy.RoundRobinSelectStrategy;
import com.sean.redis.strategy.SelectStrategy;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;

import static com.sean.redis.JedisClusterConnFactory.HOST_PORT_SEPARATOR;

/**
 * @author lixin
 * @date 2018-12-27 17:17
 **/
public class JedisNode {

    private JedisPool master;
    private List<JedisPool> slaves;
    private SelectStrategy selectStrategy;



    public JedisNode(String masterConnStr, List<String> slavesConnStrs) {
        String[] masterHostPortArray = masterConnStr.split(HOST_PORT_SEPARATOR);
        this.master = new JedisPool(new GenericObjectPoolConfig(),
                masterHostPortArray[0], Integer.valueOf(masterHostPortArray[1]));

        this.slaves = new ArrayList<JedisPool>();
        for (String slaveConnStr : slavesConnStrs) {
            String[] slaveHostPortArray = slaveConnStr
                    .split(HOST_PORT_SEPARATOR);
            this.slaves.add(new JedisPool(new GenericObjectPoolConfig(),
                    slaveHostPortArray[0], Integer
                    .valueOf(slaveHostPortArray[1])));
        }

        this.selectStrategy = new RoundRobinSelectStrategy();
    }

    public JedisPool getMaster(){
        return this.master;
    }

    /**
     * 顺序轮询获得reader实例
     * @return JedisPool
     */
    public JedisPool getRandomSlaveReader(){
        int nodeIndex =  selectStrategy.selectNode(slaves.size());
        return slaves.get(nodeIndex);
    }

}
