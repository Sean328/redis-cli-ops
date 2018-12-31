package com.xxl.redis;

import com.xxl.redis.strategy.HashShardingStrategy;
import com.xxl.redis.strategy.ShardingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixin
 * @date 2018-12-28 10:41
 **/
public class JedisClusterConnFactory extends Jedis {
    private static final Logger logger = LoggerFactory.getLogger(JedisClusterConnFactory.class);
    protected static final String HOST_PORT_SEPARATOR = ":";
    protected static final String INSTANCE_SEPARATOR = ",";

    private Boolean isRandomRead = Boolean.FALSE;

    private List<String> connectionStrs;
    private List<JedisNode> jedisNodeList = new ArrayList<>();
    private ShardingStrategy shardingStrategy = new HashShardingStrategy();

    public void init(){
        for(String connStr : this.connectionStrs){
            this.addNode(connStr);
        }
    }

    private void addNode(String connStr) {
        String[] instances =connStr.split(INSTANCE_SEPARATOR);
        String  master = instances[0];
        List<String> slaves = Arrays.asList(Arrays.copyOf(instances,instances.length));
        jedisNodeList.add(new JedisNode(master,slaves));
    }

    /**
     * 获取写操作的jedis对象
     * @param key
     * @param <T>
     * @return
     */
    protected <T> Jedis getWriter(T key){
        int nodeIndex = shardingStrategy.getKeyOnNode(key,jedisNodeList.size());
        JedisNode node = jedisNodeList.get(nodeIndex);
        return node.getMaster().getResource();
    }

    /**
     * 获取读操作的jedis对象
     * @param key
     * @param <T>
     * @return
     */
    protected <T> Jedis getReader(T key){
        int nodeIndex = shardingStrategy.getKeyOnNode(key,jedisNodeList.size());
        JedisNode node = jedisNodeList.get(nodeIndex);

        //如果开启了slave节点读操作，则采用顺序轮询获取节点
        if(isRandomRead){
            return node.getRandomSlaveReader().getResource();
        }
        return node.getMaster().getResource();
    }


    public JedisClusterConnFactory setConnectionStrs(List<String> connectionStrs) {
        this.connectionStrs = connectionStrs;
        return this;
    }

}
