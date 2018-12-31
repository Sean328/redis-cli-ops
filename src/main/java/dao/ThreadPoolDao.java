package dao;
import domain.ThreadPoolEntity;
import org.springframework.stereotype.Component;

/**
 * @author lixin
 * @date 2018-12-26 18:39
 **/
@Component
public interface ThreadPoolDao {

    public ThreadPoolEntity selectThreadPool();
}
