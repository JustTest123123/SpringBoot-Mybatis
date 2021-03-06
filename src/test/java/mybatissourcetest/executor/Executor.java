package mybatissourcetest.executor;

import com.example.DemoApplication;
import org.apache.ibatis.executor.*;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/2/25 下午9:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Executor {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    JdbcTransaction jdbcTransaction = null;
    Configuration configuration = null;
    @Before
    public void get() throws SQLException {
        jdbcTransaction = new JdbcTransaction( sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection());

        configuration = sqlSessionFactory.getConfiguration();
    }

//    会检查sql是否重复，如果重复就只进行一次预处理（Preparing）
    @Test
    public void testReuse() throws SQLException {
        ReuseExecutor s = new ReuseExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.example.mapper.UserMapper.Sel");
        List<Object> objects = s.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(10));
        System.out.println(objects);
        objects = s.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(10));
        System.out.println(objects);
    }

//    simple 会执行两次，注意是doxxx，如果是xxx那么会走base的xx会首先进行处理
    @Test
    public void testSimple() throws SQLException {
        SimpleExecutor s = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.example.mapper.UserMapper.Sel");
        List<Object> objects = s.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(10));
        System.out.println(objects);

        objects = s.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER,
                ms.getBoundSql(10));
        System.out.println(objects);
    }

//    只针对增删改操作生效,，如果是查询那么和simple一样的，也执行一次预处理
//    必须手动刷新
    @Test
    public void testBatch() throws SQLException {
        BatchExecutor s = new BatchExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.example.mapper.UserMapper.updateExecutor");
        Map<String,String> parameter = new HashMap<>();
        parameter.put("arg0","11");
        parameter.put("arg1","10");
        s.update(ms,parameter);
        s.update(ms,parameter);
//        必须手动刷新
        s.commit(true);
//        或者是下面的这个刷新
        s.flushStatements();
    }
//    bosexecutor
//    抽象出该执行器就是重复的操作,调用的是query，没有duxxx的就是base里面的抽象实现
    @Test
    public void testBaseExecutor() throws SQLException {
       BaseExecutor baseExecutor = new SimpleExecutor(sqlSessionFactory.getConfiguration(),jdbcTransaction);
       baseExecutor.query(null,null,null,null);
    }
//
    @Test
    public void testSqlSession() {
//        这里指定session中的
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.SIMPLE);
        List<Object> objects = session.selectList("com.example.mapper.UserMapper.Sel", "1");
        objects = session.selectList("com.example.mapper.UserMapper.Sel", "1");
        System.out.println(objects);
        objects = session.selectList("com.example.mapper.UserMapper.Sel", "2");
        System.out.println(objects);
    }

//    一级缓存实现

}
    