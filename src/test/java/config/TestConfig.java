package config;

import com.example.entity.Student;
import com.example.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/10 下午9:40
 */
@Slf4j
public class TestConfig {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        System.out.println(mapper.getStudents2());
        log.info("");
        Student byId = mapper.getById(1);
        System.out.println(byId);
    }
}
    