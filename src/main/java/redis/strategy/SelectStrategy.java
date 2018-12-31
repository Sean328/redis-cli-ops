package redis.strategy;

public interface SelectStrategy {

    /**
     * 从集群中获取redis实例的策略
     * @param size
     * @return
     */
    Integer selectNode(Integer size);
}
