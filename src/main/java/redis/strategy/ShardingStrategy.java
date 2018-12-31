package redis.strategy;

public interface ShardingStrategy {

    /**
     * 从多个redis集群中获取key所在集群的策略
     * @param key
     * @param size
     * @param <T>
     * @return
     */
    <T> int getKeyOnNode(T key, int size);
}
