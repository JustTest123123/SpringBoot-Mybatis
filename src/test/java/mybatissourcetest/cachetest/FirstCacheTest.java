package mybatissourcetest.cachetest;

import com.example.DemoApplication;
import com.example.mapper.UserMapper;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/2/27 下午10:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class FirstCacheTest {
//    一级缓存在BaseExecutor中PerpetualCache对象实现，二级缓存在CachingExecutor中实现
//    一级缓存默认打开，key:value形式， 底层就是hashmap，只存在当前的sqlsession中
//    二级缓存是mapper级别的，默认关闭
//    第一次调用mapper下的SQL去查询用户的信息，
//    查询到的信息会存放代该mapper对应的二级缓存区域。
//    第二次调用namespace下的mapper映射文件中，相同的sql去查询用户信息，
//    会去对应的二级缓存内取结果。

    // 总结： 清空缓存的场景：没有update，没有rollback和commit，
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
//    一级缓存生效的条件：statementId 要相同，session要相同，sql和参数要一样,没有清空缓存，没有执行update，
//    作用为LocalCacheScope.STATEMENT也会清空缓存等
    @Test
    public void testCache() {
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Object o = session.selectOne("com.example.mapper.UserMapper.Sel", "1");
        Object o1= session.selectOne("com.example.mapper.UserMapper.Sel", "1");
        System.out.println(o1 == o);
    }

//    演示一级缓存失效的场景
    @Test
    public void testCacheFail() {
        SqlSession session = sqlSessionFactory.openSession();
        SqlSession session1 = sqlSessionFactory.openSession();
        Object o = session.selectOne("com.example.mapper.UserMapper.Sel", "1");
//        调用清空缓存会使得一级缓存失效
//        session.clearCache();
        System.out.println("结果w\t" + o);

        Map<String,String> para = new HashMap<>();
        para.put("userName", UUID.randomUUID().toString().substring(0,5));
        para.put("id","1");
//调用update也会使得一级缓存失效
        session1.update("update",para);

//        如果不clearCache 查询出来的就是旧值
        session.clearCache();
        Object o1= session.selectOne("com.example.mapper.UserMapper.Sel", "1");
        System.out.println("结果w\t" + o1);

        System.out.println(o1 == o);
    }
}
    