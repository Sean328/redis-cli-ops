import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author sean
 * @date 2018/12/09/14:12
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context.xml")
public class RedisCacheTest {

    @Resource
    private JedisConnectionFactory jedisConnectionFactory;

    @Test
    public void redisCacheInstanceTest(){

        Assert.assertNotNull(jedisConnectionFactory);

    }
}
