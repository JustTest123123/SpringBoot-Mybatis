package config;

import com.example.entity.Category;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.mapper.StudentMapper;
import com.example.mapper.TeacherMapper;
import com.example.mapper.UserDaoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/3/14 下午6:09
 */
@Slf4j
public class TestLazyLoad {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        List<Teacher> allTeacher = mapper.getAllTeacher();
        allTeacher.forEach(e -> System.out.println(e.getId()));
        allTeacher.forEach(e -> System.out.println(e.getStudents()));
        session.close();
    }
    @Test
    public void test() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        UserDaoMapper u =  session.getMapper(UserDaoMapper.class);//用动态代理的方式自动生成接口的实现类
        List<Category> lst  =  u.findCategoryWithLazingload();
        for (Category c : lst) {
            System.out.println(c.getCname());
        }
        System.out.println(lst);
        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        List<Teacher> allTeacherWithLazy = mapper.getAllTeacherWithLazy();
        log.info("{}");

        session.close();
    }
}
    