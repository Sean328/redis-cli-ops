import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.JedisConnectionFactory;

/**
 * @author lixin
 * @date 2018-12-27 15:51
 **/
public class JedisCli<T> extends JedisConnectionFactory {

    private static final Logger logger = LoggerFactory.getLogger(JedisCli.class);

    public JedisCli(){
        super();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public String get(String key){
        return jedisPool.getResource().get(key);
    }

    @Override
    public String set(String key , String  value ){
        return jedisPool.getResource().set(key,value);
    }

}
