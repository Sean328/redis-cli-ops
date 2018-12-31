import com.sean.JedisCli;
import com.sean.JedisClusterCli;
import com.sean.dao.ThreadPoolDao;
import com.sean.domain.ThreadPoolEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author sean
 * @date 2018/12/09/14:12
 **/

public class RedisCacheTest extends BaseSpringTest {

    @Resource
    private JedisConnectionFactory jedisConnectionFactory;
    @Resource
    private ThreadPoolDao threadPoolDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private JedisCli jedisCli;

    @Resource
    private JedisClusterCli jedisClusterCli;

    @Test
    public void redisCacheInstanceTest(){

        Assert.assertNotNull(jedisConnectionFactory);

        System.out.println(jedisConnectionFactory.toString());

    }

    @Test
    public void springCacheRedisTest(){
        Assert.assertNotNull(redisTemplate);
        redisTemplate.opsForValue().set("lixin","sean");
        String sb  = (String) redisTemplate.opsForValue().get("lixin");
        System.out.println(sb.toString());
        System.out.println(sb.hashCode());
    }

    @Test
    public void jedisCacheTest(){
        jedisCli.set("name","lixin");
        System.out.println(jedisCli.get("name"));
        System.out.println(jedisCli.get("name").hashCode());
    }

    @Test
    public void jedisClusterTest(){
        jedisClusterCli.set("name","lixin");
        System.out.println(jedisClusterCli.get("name"));
        System.out.println(jedisClusterCli.get("name").hashCode());
    }

    @Test
    public void mybatisSqlTest() {
        ThreadPoolEntity threadPoolEntity = threadPoolDao.selectThreadPool();
        System.out.println(threadPoolEntity);
        System.out.println(threadPoolEntity.hashCode());
    }
}
