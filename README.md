十一、SpringBoot整合Mybatis吐血整理

2019年3月5日, 星期二
19:47

写在前面：首先就是跟你之前的SSM整合不同的是，该整合可以直接是：实体类maper和实体类example，之前的整合我也不知道哪里出了问题，反正老是报底下这个错误



然后我就重新整理了一份整合流程：
1、整个的项目框架是这样的
success_user.sql文件内容，建立表，我的数据库在crud中
CREATE TABLE `success_user` (
  `uid` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) DEFAULT NULL,
  `password` VARCHAR(64) DEFAULT NULL,
  `email` VARCHAR(200) DEFAULT NULL,
  `address` VARCHAR(200) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO success_user VALUES(3,'2','2','3','343')

2、application.properties文件
配置数据源，使用的数据库连接池以及连接的数据库，账号和密码
为了防止和其他的项目冲突，这里的port改成了80，启动时需要注意一下


server.port=80
#spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/crud?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.initialSize=20
spring.datasource.minIdle=10
spring.datasource.maxActive=100

logging.level.com.dao=DEBUG

3、启动类的内容（启动类放在所有包的根下，防止扫描不到）
名称：StartApplication.java

packagecom;

importcom.alibaba.druid.pool.DruidDataSource;
importorg.apache.ibatis.session.SqlSessionFactory;
importorg.mybatis.spring.SqlSessionFactoryBean;
importorg.mybatis.spring.annotation.MapperScan;
importorg.springframework.boot.Banner;
importorg.springframework.boot.SpringApplication;
importorg.springframework.boot.autoconfigure.EnableAutoConfiguration;
importorg.springframework.boot.autoconfigure.SpringBootApplication;
importorg.springframework.boot.context.properties.ConfigurationProperties;
importorg.springframework.context.annotation.Bean;
importorg.springframework.context.annotation.ComponentScan;
importorg.springframework.core.io.support.PathMatchingResourcePatternResolver;

importjavax.sql.DataSource;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
//扫描你的dao包
@MapperScan("com.dao")
publicclassStartApplication{
publicstaticvoidmain(String[]args)throwsException{
SpringApplicationapp=newSpringApplication(StartApplication.class);
app.setBannerMode(Banner.Mode.OFF);
app.run(args);
}

//datasource注入
@Bean
@ConfigurationProperties(prefix="spring.datasource")
publicDataSourcedataSource(){
returnnewDruidDataSource();
}

//mybatisSQLSession注入
@Bean
publicSqlSessionFactorysqlSessionFactoryBean()throwsException{
SqlSessionFactoryBeansqlSessionFactoryBean=newSqlSessionFactoryBean();
sqlSessionFactoryBean.setDataSource(dataSource());
PathMatchingResourcePatternResolverresolver=newPathMatchingResourcePatternResolver();
sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
returnsqlSessionFactoryBean.getObject();
}
}


3、UserVoMapper.xml内容
该文件放在resources下的mapper文件夹下


<?xmlversion="1.0"encoding="UTF-8"?>
<!DOCTYPEmapperPUBLIC"-//mybatis.org//DTDMapper3.0//EN""http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mappernamespace="com.dao.UserVoMapper">
<resultMapid="BaseResultMap"type="com.domain.vo.UserVo">
<idcolumn="uid"jdbcType="INTEGER"property="uid"/>
<resultcolumn="username"jdbcType="VARCHAR"property="username"/>
<resultcolumn="password"jdbcType="VARCHAR"property="password"/>
<resultcolumn="email"jdbcType="VARCHAR"property="email"/>
<resultcolumn="address"jdbcType="VARCHAR"property="address"/>
</resultMap>
<sqlid="Example_Where_Clause">
<where>
<foreachcollection="oredCriteria"item="criteria"separator="or">
<iftest="criteria.valid">
<trimprefix="("prefixOverrides="and"suffix=")">
<foreachcollection="criteria.criteria"item="criterion">
<choose>
<whentest="criterion.noValue">
and${criterion.condition}
</when>
<whentest="criterion.singleValue">
and${criterion.condition}#{criterion.value}
</when>
<whentest="criterion.betweenValue">
and${criterion.condition}#{criterion.value}and#{criterion.secondValue}
</when>
<whentest="criterion.listValue">
and${criterion.condition}
<foreachclose=")"collection="criterion.value"item="listItem"open="("separator=",">
#{listItem}
</foreach>
</when>
</choose>
</foreach>
</trim>
</if>
</foreach>
</where>
</sql>
<sqlid="Update_By_Example_Where_Clause">
<where>
<foreachcollection="example.oredCriteria"item="criteria"separator="or">
<iftest="criteria.valid">
<trimprefix="("prefixOverrides="and"suffix=")">
<foreachcollection="criteria.criteria"item="criterion">
<choose>
<whentest="criterion.noValue">
and${criterion.condition}
</when>
<whentest="criterion.singleValue">
and${criterion.condition}#{criterion.value}
</when>
<whentest="criterion.betweenValue">
and${criterion.condition}#{criterion.value}and#{criterion.secondValue}
</when>
<whentest="criterion.listValue">
and${criterion.condition}
<foreachclose=")"collection="criterion.value"item="listItem"open="("separator=",">
#{listItem}
</foreach>
</when>
</choose>
</foreach>
</trim>
</if>
</foreach>
</where>
</sql>
<sqlid="Base_Column_List">
uid,username,password,email,address
</sql>
<selectid="selectByExample"parameterType="com.domain.vo.UserVoExample"resultMap="BaseResultMap">
select
<iftest="distinct">
distinct
</if>
<includerefid="Base_Column_List"/>
fromsuccess_user
<iftest="_parameter!=null">
<includerefid="Example_Where_Clause"/>
</if>
<iftest="orderByClause!=null">
orderby${orderByClause}
</if>
</select>
<selectid="selectByPrimaryKey"parameterType="java.lang.Integer"resultMap="BaseResultMap">
select
<includerefid="Base_Column_List"/>
fromsuccess_user
whereuid=#{uid,jdbcType=INTEGER}
</select>
<deleteid="deleteByPrimaryKey"parameterType="java.lang.Integer">
deletefromsuccess_user
whereuid=#{uid,jdbcType=INTEGER}
</delete>
<deleteid="deleteByExample"parameterType="com.domain.vo.UserVoExample">
deletefromsuccess_user
<iftest="_parameter!=null">
<includerefid="Example_Where_Clause"/>
</if>
</delete>
<insertid="insert"parameterType="com.domain.vo.UserVo">
insertintosuccess_user(uid,username,password,
email,address)
values(#{uid,jdbcType=INTEGER},#{username,jdbcType=VARCHAR},#{password,jdbcType=VARCHAR},
#{email,jdbcType=VARCHAR},#{address,jdbcType=VARCHAR})
</insert>
<insertid="insertSelective"parameterType="com.domain.vo.UserVo">
insertintosuccess_user
<trimprefix="("suffix=")"suffixOverrides=",">
<iftest="uid!=null">
uid,
</if>
<iftest="username!=null">
username,
</if>
<iftest="password!=null">
password,
</if>
<iftest="email!=null">
email,
</if>
<iftest="address!=null">
address,
</if>
</trim>
<trimprefix="values("suffix=")"suffixOverrides=",">
<iftest="uid!=null">
#{uid,jdbcType=INTEGER},
</if>
<iftest="username!=null">
#{username,jdbcType=VARCHAR},
</if>
<iftest="password!=null">
#{password,jdbcType=VARCHAR},
</if>
<iftest="email!=null">
#{email,jdbcType=VARCHAR},
</if>
<iftest="address!=null">
#{address,jdbcType=VARCHAR},
</if>
</trim>
</insert>
<selectid="countByExample"parameterType="com.domain.vo.UserVoExample"resultType="java.lang.Long">
selectcount(*)fromsuccess_user
<iftest="_parameter!=null">
<includerefid="Example_Where_Clause"/>
</if>
</select>
<updateid="updateByExampleSelective"parameterType="map">
updatesuccess_user
<set>
<iftest="record.uid!=null">
uid=#{record.uid,jdbcType=INTEGER},
</if>
<iftest="record.username!=null">
username=#{record.username,jdbcType=VARCHAR},
</if>
<iftest="record.password!=null">
password=#{record.password,jdbcType=VARCHAR},
</if>
<iftest="record.email!=null">
email=#{record.email,jdbcType=VARCHAR},
</if>
<iftest="record.address!=null">
address=#{record.address,jdbcType=VARCHAR},
</if>
</set>
<iftest="_parameter!=null">
<includerefid="Update_By_Example_Where_Clause"/>
</if>
</update>
<updateid="updateByExample"parameterType="map">
updatesuccess_user
setuid=#{record.uid,jdbcType=INTEGER},
username=#{record.username,jdbcType=VARCHAR},
password=#{record.password,jdbcType=VARCHAR},
email=#{record.email,jdbcType=VARCHAR},
address=#{record.address,jdbcType=VARCHAR}
<iftest="_parameter!=null">
<includerefid="Update_By_Example_Where_Clause"/>
</if>
</update>
<updateid="updateByPrimaryKeySelective"parameterType="com.domain.vo.UserVo">
updatesuccess_user
<set>
<iftest="username!=null">
username=#{username,jdbcType=VARCHAR},
</if>
<iftest="password!=null">
password=#{password,jdbcType=VARCHAR},
</if>
<iftest="email!=null">
email=#{email,jdbcType=VARCHAR},
</if>
<iftest="address!=null">
address=#{address,jdbcType=VARCHAR},
</if>
</set>
whereuid=#{uid,jdbcType=INTEGER}
</update>
<updateid="updateByPrimaryKey"parameterType="com.domain.vo.UserVo">
updatesuccess_user
setusername=#{username,jdbcType=VARCHAR},
password=#{password,jdbcType=VARCHAR},
email=#{email,jdbcType=VARCHAR},
address=#{address,jdbcType=VARCHAR}
whereuid=#{uid,jdbcType=INTEGER}
</update>
</mapper>


4、IndexController.java
表现层实现
packagecom.controller;

importcom.dao.UserVoMapper;
importcom.domain.vo.UserVo;
importcom.service.IndexService;
importorg.springframework.beans.factory.annotation.Autowired;
importorg.springframework.stereotype.Controller;
importorg.springframework.web.bind.annotation.GetMapping;
importorg.springframework.web.bind.annotation.RequestMapping;
importorg.springframework.web.bind.annotation.ResponseBody;
importorg.springframework.web.bind.annotation.RestController;

importjava.util.List;

/**
*@authortangj
*@date2018/4/2522:54
*/
@RestController
publicclassIndexController{

@Autowired
UserVoMapperuserDao;

@Autowired
privateIndexServiceindexService;

@RequestMapping("/index")
publicUserVoindex(){
UserVouserVo=indexService.getinde(1);
System.out.println(userVo.toString());
returnuserVo;
}
}

6、UserVoMapper.java
packagecom.dao;

importcom.domain.vo.UserVo;
importcom.domain.vo.UserVoExample;
importjava.util.List;
importorg.apache.ibatis.annotations.Param;

publicinterfaceUserVoMapper{
longcountByExample(UserVoExampleexample);

intdeleteByExample(UserVoExampleexample);

intdeleteByPrimaryKey(Integeruid);

intinsert(UserVorecord);

intinsertSelective(UserVorecord);

List<UserVo>selectByExample(UserVoExampleexample);

UserVoselectByPrimaryKey(Integeruid);

intupdateByExampleSelective(@Param("record")UserVorecord,@Param("example")UserVoExampleexample);

intupdateByExample(@Param("record")UserVorecord,@Param("example")UserVoExampleexample);

intupdateByPrimaryKeySelective(UserVorecord);

intupdateByPrimaryKey(UserVorecord);
}

7、UserVo.java 
pojo类

packagecom.domain.vo;

publicclassUserVo{
privateIntegeruid;

privateStringusername;

privateStringpassword;

privateStringemail;

privateStringaddress;

publicIntegergetUid(){
returnuid;
}

publicvoidsetUid(Integeruid){
this.uid=uid;
}

publicStringgetUsername(){
returnusername;
}

publicvoidsetUsername(Stringusername){
this.username=username==null?null:username.trim();
}

publicStringgetPassword(){
returnpassword;
}

publicvoidsetPassword(Stringpassword){
this.password=password==null?null:password.trim();
}

publicStringgetEmail(){
returnemail;
}

publicvoidsetEmail(Stringemail){
this.email=email==null?null:email.trim();
}

publicStringgetAddress(){
returnaddress;
}

publicvoidsetAddress(Stringaddress){
this.address=address==null?null:address.trim();
}

@Override
publicStringtoString(){
return"UserVo{"+
"uid="+uid+
",username='"+username+'\''+
",password='"+password+'\''+
",email='"+email+'\''+
",address='"+address+'\''+
'}';
}
}
8、UserVoExample.java
实现复杂的查询，这里就复杂一点
packagecom.domain.vo;

importjava.util.ArrayList;
importjava.util.List;

publicclassUserVoExample{
protectedStringorderByClause;

protectedbooleandistinct;

protectedList<Criteria>oredCriteria;

publicUserVoExample(){
oredCriteria=newArrayList<Criteria>();
}

publicvoidsetOrderByClause(StringorderByClause){
this.orderByClause=orderByClause;
}

publicStringgetOrderByClause(){
returnorderByClause;
}

publicvoidsetDistinct(booleandistinct){
this.distinct=distinct;
}

publicbooleanisDistinct(){
returndistinct;
}

publicList<Criteria>getOredCriteria(){
returnoredCriteria;
}

publicvoidor(Criteriacriteria){
oredCriteria.add(criteria);
}

publicCriteriaor(){
Criteriacriteria=createCriteriaInternal();
oredCriteria.add(criteria);
returncriteria;
}

publicCriteriacreateCriteria(){
Criteriacriteria=createCriteriaInternal();
if(oredCriteria.size()==0){
oredCriteria.add(criteria);
}
returncriteria;
}

protectedCriteriacreateCriteriaInternal(){
Criteriacriteria=newCriteria();
returncriteria;
}

publicvoidclear(){
oredCriteria.clear();
orderByClause=null;
distinct=false;
}

protectedabstractstaticclassGeneratedCriteria{
protectedList<Criterion>criteria;

protectedGeneratedCriteria(){
super();
criteria=newArrayList<Criterion>();
}

publicbooleanisValid(){
returncriteria.size()>0;
}

publicList<Criterion>getAllCriteria(){
returncriteria;
}

publicList<Criterion>getCriteria(){
returncriteria;
}

protectedvoidaddCriterion(Stringcondition){
if(condition==null){
thrownewRuntimeException("Valueforconditioncannotbenull");
}
criteria.add(newCriterion(condition));
}

protectedvoidaddCriterion(Stringcondition,Objectvalue,Stringproperty){
if(value==null){
thrownewRuntimeException("Valuefor"+property+"cannotbenull");
}
criteria.add(newCriterion(condition,value));
}

protectedvoidaddCriterion(Stringcondition,Objectvalue1,Objectvalue2,Stringproperty){
if(value1==null||value2==null){
thrownewRuntimeException("Betweenvaluesfor"+property+"cannotbenull");
}
criteria.add(newCriterion(condition,value1,value2));
}

publicCriteriaandUidIsNull(){
addCriterion("uidisnull");
return(Criteria)this;
}

publicCriteriaandUidIsNotNull(){
addCriterion("uidisnotnull");
return(Criteria)this;
}

publicCriteriaandUidEqualTo(Integervalue){
addCriterion("uid=",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidNotEqualTo(Integervalue){
addCriterion("uid<>",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidGreaterThan(Integervalue){
addCriterion("uid>",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidGreaterThanOrEqualTo(Integervalue){
addCriterion("uid>=",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidLessThan(Integervalue){
addCriterion("uid<",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidLessThanOrEqualTo(Integervalue){
addCriterion("uid<=",value,"uid");
return(Criteria)this;
}

publicCriteriaandUidIn(List<Integer>values){
addCriterion("uidin",values,"uid");
return(Criteria)this;
}

publicCriteriaandUidNotIn(List<Integer>values){
addCriterion("uidnotin",values,"uid");
return(Criteria)this;
}

publicCriteriaandUidBetween(Integervalue1,Integervalue2){
addCriterion("uidbetween",value1,value2,"uid");
return(Criteria)this;
}

publicCriteriaandUidNotBetween(Integervalue1,Integervalue2){
addCriterion("uidnotbetween",value1,value2,"uid");
return(Criteria)this;
}

publicCriteriaandUsernameIsNull(){
addCriterion("usernameisnull");
return(Criteria)this;
}

publicCriteriaandUsernameIsNotNull(){
addCriterion("usernameisnotnull");
return(Criteria)this;
}

publicCriteriaandUsernameEqualTo(Stringvalue){
addCriterion("username=",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameNotEqualTo(Stringvalue){
addCriterion("username<>",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameGreaterThan(Stringvalue){
addCriterion("username>",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameGreaterThanOrEqualTo(Stringvalue){
addCriterion("username>=",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameLessThan(Stringvalue){
addCriterion("username<",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameLessThanOrEqualTo(Stringvalue){
addCriterion("username<=",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameLike(Stringvalue){
addCriterion("usernamelike",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameNotLike(Stringvalue){
addCriterion("usernamenotlike",value,"username");
return(Criteria)this;
}

publicCriteriaandUsernameIn(List<String>values){
addCriterion("usernamein",values,"username");
return(Criteria)this;
}

publicCriteriaandUsernameNotIn(List<String>values){
addCriterion("usernamenotin",values,"username");
return(Criteria)this;
}

publicCriteriaandUsernameBetween(Stringvalue1,Stringvalue2){
addCriterion("usernamebetween",value1,value2,"username");
return(Criteria)this;
}

publicCriteriaandUsernameNotBetween(Stringvalue1,Stringvalue2){
addCriterion("usernamenotbetween",value1,value2,"username");
return(Criteria)this;
}

publicCriteriaandPasswordIsNull(){
addCriterion("passwordisnull");
return(Criteria)this;
}

publicCriteriaandPasswordIsNotNull(){
addCriterion("passwordisnotnull");
return(Criteria)this;
}

publicCriteriaandPasswordEqualTo(Stringvalue){
addCriterion("password=",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordNotEqualTo(Stringvalue){
addCriterion("password<>",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordGreaterThan(Stringvalue){
addCriterion("password>",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordGreaterThanOrEqualTo(Stringvalue){
addCriterion("password>=",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordLessThan(Stringvalue){
addCriterion("password<",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordLessThanOrEqualTo(Stringvalue){
addCriterion("password<=",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordLike(Stringvalue){
addCriterion("passwordlike",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordNotLike(Stringvalue){
addCriterion("passwordnotlike",value,"password");
return(Criteria)this;
}

publicCriteriaandPasswordIn(List<String>values){
addCriterion("passwordin",values,"password");
return(Criteria)this;
}

publicCriteriaandPasswordNotIn(List<String>values){
addCriterion("passwordnotin",values,"password");
return(Criteria)this;
}

publicCriteriaandPasswordBetween(Stringvalue1,Stringvalue2){
addCriterion("passwordbetween",value1,value2,"password");
return(Criteria)this;
}

publicCriteriaandPasswordNotBetween(Stringvalue1,Stringvalue2){
addCriterion("passwordnotbetween",value1,value2,"password");
return(Criteria)this;
}

publicCriteriaandEmailIsNull(){
addCriterion("emailisnull");
return(Criteria)this;
}

publicCriteriaandEmailIsNotNull(){
addCriterion("emailisnotnull");
return(Criteria)this;
}

publicCriteriaandEmailEqualTo(Stringvalue){
addCriterion("email=",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailNotEqualTo(Stringvalue){
addCriterion("email<>",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailGreaterThan(Stringvalue){
addCriterion("email>",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailGreaterThanOrEqualTo(Stringvalue){
addCriterion("email>=",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailLessThan(Stringvalue){
addCriterion("email<",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailLessThanOrEqualTo(Stringvalue){
addCriterion("email<=",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailLike(Stringvalue){
addCriterion("emaillike",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailNotLike(Stringvalue){
addCriterion("emailnotlike",value,"email");
return(Criteria)this;
}

publicCriteriaandEmailIn(List<String>values){
addCriterion("emailin",values,"email");
return(Criteria)this;
}

publicCriteriaandEmailNotIn(List<String>values){
addCriterion("emailnotin",values,"email");
return(Criteria)this;
}

publicCriteriaandEmailBetween(Stringvalue1,Stringvalue2){
addCriterion("emailbetween",value1,value2,"email");
return(Criteria)this;
}

publicCriteriaandEmailNotBetween(Stringvalue1,Stringvalue2){
addCriterion("emailnotbetween",value1,value2,"email");
return(Criteria)this;
}

publicCriteriaandAddressIsNull(){
addCriterion("addressisnull");
return(Criteria)this;
}

publicCriteriaandAddressIsNotNull(){
addCriterion("addressisnotnull");
return(Criteria)this;
}

publicCriteriaandAddressEqualTo(Stringvalue){
addCriterion("address=",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressNotEqualTo(Stringvalue){
addCriterion("address<>",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressGreaterThan(Stringvalue){
addCriterion("address>",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressGreaterThanOrEqualTo(Stringvalue){
addCriterion("address>=",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressLessThan(Stringvalue){
addCriterion("address<",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressLessThanOrEqualTo(Stringvalue){
addCriterion("address<=",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressLike(Stringvalue){
addCriterion("addresslike",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressNotLike(Stringvalue){
addCriterion("addressnotlike",value,"address");
return(Criteria)this;
}

publicCriteriaandAddressIn(List<String>values){
addCriterion("addressin",values,"address");
return(Criteria)this;
}

publicCriteriaandAddressNotIn(List<String>values){
addCriterion("addressnotin",values,"address");
return(Criteria)this;
}

publicCriteriaandAddressBetween(Stringvalue1,Stringvalue2){
addCriterion("addressbetween",value1,value2,"address");
return(Criteria)this;
}

publicCriteriaandAddressNotBetween(Stringvalue1,Stringvalue2){
addCriterion("addressnotbetween",value1,value2,"address");
return(Criteria)this;
}
}

publicstaticclassCriteriaextendsGeneratedCriteria{

protectedCriteria(){
super();
}
}

publicstaticclassCriterion{
privateStringcondition;

privateObjectvalue;

privateObjectsecondValue;

privatebooleannoValue;

privatebooleansingleValue;

privatebooleanbetweenValue;

privatebooleanlistValue;

privateStringtypeHandler;

publicStringgetCondition(){
returncondition;
}

publicObjectgetValue(){
returnvalue;
}

publicObjectgetSecondValue(){
returnsecondValue;
}

publicbooleanisNoValue(){
returnnoValue;
}

publicbooleanisSingleValue(){
returnsingleValue;
}

publicbooleanisBetweenValue(){
returnbetweenValue;
}

publicbooleanisListValue(){
returnlistValue;
}

publicStringgetTypeHandler(){
returntypeHandler;
}

protectedCriterion(Stringcondition){
super();
this.condition=condition;
this.typeHandler=null;
this.noValue=true;
}

protectedCriterion(Stringcondition,Objectvalue,StringtypeHandler){
super();
this.condition=condition;
this.value=value;
this.typeHandler=typeHandler;
if(valueinstanceofList<?>){
this.listValue=true;
}else{
this.singleValue=true;
}
}

protectedCriterion(Stringcondition,Objectvalue){
this(condition,value,null);
}

protectedCriterion(Stringcondition,Objectvalue,ObjectsecondValue,StringtypeHandler){
super();
this.condition=condition;
this.value=value;
this.secondValue=secondValue;
this.typeHandler=typeHandler;
this.betweenValue=true;
}

protectedCriterion(Stringcondition,Objectvalue,ObjectsecondValue){
this(condition,value,secondValue,null);
}
}
}

10、IndexService.java
服务层接口
packagecom.service.impl;

importcom.dao.UserVoMapper;
importcom.domain.vo.UserVo;
importcom.service.IndexService;
importorg.springframework.beans.factory.annotation.Autowired;
importorg.springframework.stereotype.Service;

@Service
publicclassIndexServiceimplimplementsIndexService{
@Autowired
privateUserVoMapperuserVoMapper;
@Override
publicUserVogetinde(intid){
returnuserVoMapper.selectByPrimaryKey(id);
}
}

11、IndexServiceimpl.java
服务层的实现类
packagecom.service.impl;

importcom.dao.UserVoMapper;
importcom.domain.vo.UserVo;
importcom.service.IndexService;
importorg.springframework.beans.factory.annotation.Autowired;
importorg.springframework.stereotype.Service;

@Service
publicclassIndexServiceimplimplementsIndexService{
@Autowired
privateUserVoMapperuserVoMapper;
@Override
publicUserVogetinde(intid){
returnuserVoMapper.selectByPrimaryKey(id);
}
}

执行启动类：
浏览器输入：
http://localhost:80/index?id=3
发现出现这个

成功
这个是带example的，接下来我们尝试一下直接使用mapper进行

整个项目的文件图

github地址：
https://github.com/1791273509/SpringBoot-Mybatis.git
