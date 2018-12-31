
import domain.ThreadPoolEntity;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lixin
 * @date 2018-12-26 11:00
 **/
public class MyBatisTest {

    public static void main(String[] args) throws IOException {

        String resource = "mybatis/mybaties-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        SqlSession session = sqlSessionFactory.openSession();
        try {
            String statement = "mapper.threadpoolMapper.selectThreadPool";//映射sql的标识字符串
            ThreadPoolEntity entity = (ThreadPoolEntity) session.selectOne(statement);
            System.out.println(entity);
        } finally {
            session.close();
        }

    }
}
