package mybatissourcetest.cachetest;

import com.example.DemoApplication;
import com.example.entity.User;
import com.example.mapper.StudentMapper;
import com.example.mapper.UserMapper;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wenbaox
 * @version 1.0
 * @date 2021/2/28 下午6:05
 */
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class SecondCacheTest {
//    二级缓存优于一级缓存的执行，在CachingExecutor中实现

    /**
     * 二级缓存也称作是应用级缓存，与一级缓存不同的是它的作用范围是整个应用，而且
     * 可以跨线程使用。
     * 所以二级缓存有更高的命中率，适合一些修改较少的数据量
     * 二级缓存采用责任链和装饰者模式，org.apache.ibatis.cache.Cache 实现这些
     * 接口的类都是各个实现
     */
//    二级缓存存在于内存中如果应用挂掉或者重启，数据消失
//    mybatis 采用责任链模式来实现各个功能
//
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Test
    public void test() {
//        一定要在对应的mapper中开启二级缓存
        Configuration configuration = sqlSessionFactory.getConfiguration();
        System.out.println(sqlSessionFactory.openSession().getConfiguration().getCache("com.example.mapper" +
                ".UserMapper"));
    }

    //二级缓存命中条件： 会话提交后，sql语句、参数相同；相同的StatementID，RowBounds相同 (使用二级缓存要在UserMapper 中加上<cache></cache>标签，表示开启)
    /*二级缓存
    默认情况下，mybatis打开了二级缓存，但它并未生效，因为二级缓存的作用域是namespace，所以还需要在Mapper.xml文件中配置一下才能使二级缓存生效
            单表二级缓存
    下面对userMapper.xml配置一下，让其二级缓存生效,只需加入cache标签即可 （https://www.cnblogs.com/zhengxl5566/p/11868656.html）
    */
//m命中条件1：必须提交
//    针对注解和xml不能同时使用cache，需要使用ref ：详细见 https://blog.csdn.net/lovely960823/article/details/111277801?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control&dist_request_id=d610018e-be92-4d67-bb4d-93826d28b8c2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.control
//    在xml或者是接口中指明cache然后另一个地方指明ref 这里就是xml中ref，在接口中指明cache
    @Autowired
    private StudentMapper studentMapper;
    @Test
    public void commit() {
        SqlSession session = sqlSessionFactory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        System.out.println(mapper.Sel(1));
//        只有提交了之后二级缓存才生效，存在于不同的会话
        session.commit();
        SqlSession session1 = sqlSessionFactory.openSession();
        UserMapper mapper1 = session1.getMapper(UserMapper.class);
        System.out.println(mapper1.Sel(1));

    }
//    为什么二级缓存必须提交之后才生效？ 因为二级缓存是跨会话的
//    源码中的delete表示的就是缓存，tcm表示暂存区，每次修改只有commit之后才会提交，更新到缓存区
//    CachingExecutor 这是关于二级缓存的类
}
    