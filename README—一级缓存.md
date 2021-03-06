[mybatis 一级缓存](https://blog.csdn.net/luanlouis/article/details/41280959)

实际上, MyBatis只是一个MyBatis对外的接口，
SqlSession将它的工作交给了Executor执行器这个角色来完成，
负责完成对数据库的各种操作。当创建了一个SqlSession对象时，
MyBatis会为这个SqlSession对象创建一个新的Executor执行器，
而缓存信息就被维护在这个Executor执行器中，
MyBatis将缓存和对缓存相关的操作封装成了Cache接口中。
SqlSession、Executor、Cache之间的关系如下列类图所示：
如上述的类图所示，
Executor接口的实现类BaseExecutor中拥有一个Cache接口的实现类PerpetualCache，
则对于BaseExecutor对象而言，它将使用PerpetualCache对象维护缓存。
综上，SqlSession对象、Executor对象、Cache对象之间的关系如下图所示：

## 一级缓存的生命周期
- MyBatis在开启一个数据库会话时，会创建一个新的SqlSession对象，SqlSession对象中会有一个新的Executor对象，Executor对象中持有一个新的PerpetualCache对象；
当会话结束时，SqlSession对象及其内部的Executor对象还有PerpetualCache对象也一并释放掉。
- 如果SqlSession调用了close()方法，会释放掉一级缓存PerpetualCache对象，一级缓存将不可用；
- 如果SqlSession调用了clearCache()，会清空PerpetualCache对象中的数据，但是该对象仍可使用；
- SqlSession中执行了任何一个update操作(update()、delete()、insert()) ，都会清空PerpetualCache对象的数据，但是该对象可以继续使用，一级缓存失效的场景
只要执行了增、删、改，都会清除；
## 总结
一个是SqlSession对应一个Executor，一个Executor对应一个PerpetualCache对象(一级缓存)