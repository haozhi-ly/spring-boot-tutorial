package com.ly.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//@Configuration
//@MapperScan(value = "com.ly.dao.test",sqlSessionFactoryRef = "testSqlSessionFactory")
public class SpringConfig {

    @Autowired
    private Environment env;

	@Bean("testDataSource")
	@ConfigurationProperties(prefix="spring.test-datasource")
	public DataSource dataSource(){
		DruidDataSource shiroDataSource = new DruidDataSource();
		return shiroDataSource;
	}

	@Bean("testSqlSessionFactory")
	//@ConfigurationProperties(prefix="test-mybatis")
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("testDataSource") DataSource shiroDataSource) throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(shiroDataSource);
		//该配置非常的重要，如果不将PageInterceptor设置到SqlSessionFactoryBean中，导致分页失效
		//fb.setPlugins(new Interceptor[]{pageInterceptor});
		fb.setTypeAliasesPackage(env.getProperty("test-mybatis.type-aliases-package"));
		fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("test-mybatis.mapper-locations")));
		return fb;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("testSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
