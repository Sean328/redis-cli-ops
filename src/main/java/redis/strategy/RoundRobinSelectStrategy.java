package redis.strategy;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author lixin
 * @date 2018-12-28 11:35
 **/
public class RoundRobinSelectStrategy implements SelectStrategy {
    private AtomicLong iter = new AtomicLong(0);

    @Override
    public Integer selectNode(Integer size) {
        long iterValue = iter.incrementAndGet();
        if(iterValue == Long.MAX_VALUE){
            iter.set(0);
        }

        return (int) iterValue % size;
    }
}
