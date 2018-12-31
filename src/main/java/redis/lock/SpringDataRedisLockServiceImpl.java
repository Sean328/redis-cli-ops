package redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * @author sean
 * @date 2018/12/09/23:33
 **/
public class SpringDataRedisLockServiceImpl implements SpringDataRedisLockService {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataRedisLockServiceImpl.class);
    @Resource
    private RedisTemplate redisTemplate;
    private long defaultLockTimeMillis = 10 * 60 * 1000;


    private long defaultTryLockIntervalMillis = 1000;

    /**
     * 默认锁10分钟
     *
     * @param key
     * @return
     */
    @Override
    public boolean lock(String key) {
        return lock(key, defaultLockTimeMillis);
    }

    /**
     * @param key
     * @param lockMillis
     * @return
     */
    @Override
    public boolean lock(String key, long lockMillis) {
        if (redisTemplate.opsForValue().setIfAbsent(key, System.currentTimeMillis() + lockMillis)) {
            redisTemplate.expire(key, lockMillis + 3000, TimeUnit.MILLISECONDS);
            return true;
        }

        String lockExpireTime = String.valueOf(redisTemplate.opsForValue().get(key));
        //key被清理，锁被释放
        if (StringUtils.isEmpty(lockExpireTime) || "null".equals(lockExpireTime)) {
            logger.warn("{}获取锁失败，可以被清理或锁被释放", key);
            return false;
        }
        // 锁未过期
        if (Long.parseLong(lockExpireTime) > System.currentTimeMillis()) {
            logger.warn("{}获取锁失败，锁未过期", key);
            return false;
        }

        String nowLowExpireTime = String.valueOf(redisTemplate.opsForValue().getAndSet(key, System.currentTimeMillis() + lockMillis));
        // key不存在，已被清理，拿锁成功
        if (StringUtils.isEmpty(nowLowExpireTime) || "null".equals(nowLowExpireTime)) {
            redisTemplate.expire(key, lockMillis + 3000, TimeUnit.MILLISECONDS);
            return true;
        } else if (lockExpireTime.equals(nowLowExpireTime)) {
            redisTemplate.expire(key, lockMillis + 3000, TimeUnit.MILLISECONDS);
            return true;
        }

        logger.warn("{}获取锁失败", key);

        return false;
    }

    /**
     * 默认重试间隔时间为1s
     *
     * @param key
     * @return
     */
    @Override
    public boolean tryLock(String key) {
        return tryLockWithLockTime(key, defaultLockTimeMillis, defaultTryLockIntervalMillis);
    }

    /**
     * @param key
     * @param intervalMillis
     * @return
     */
    @Override
    public boolean tryLock(String key, long intervalMillis) {
        return tryLockWithLockTime(key, defaultLockTimeMillis, intervalMillis);
    }

    @Override
    public boolean tryLockWithLockTime(String key, long lockMillis) {
        return tryLockWithLockTime(key, lockMillis, defaultTryLockIntervalMillis);
    }

    /**
     * 加锁失败重试3次，重试间隔时间为：次数*intervalMillis
     *
     * @param key
     * @param lockMillis
     * @param intervalMillis
     * @return
     */
    @Override
    public boolean tryLockWithLockTime(String key, long lockMillis, long intervalMillis) {
        Assert.isTrue(intervalMillis > 0);
        Assert.isTrue(lockMillis > 0);

        int tryTimes = 0;
        while (tryTimes < 3) {
            boolean succeed = this.lock(key, lockMillis);
            if (succeed) {
                return succeed;
            }
            tryTimes++;
            logger.debug("tryLock try:{},key:{}", tryTimes, key);
            try {
                Thread.sleep(intervalMillis * tryTimes);
            } catch (InterruptedException e) {
                logger.warn("key:{} try lock fail", key, e);
            }
        }
        return false;
    }

    @Override
    public void unLock(String key) {
        // 10毫秒后过期
        redisTemplate.expire(key, 0, TimeUnit.MILLISECONDS);
    }
}
