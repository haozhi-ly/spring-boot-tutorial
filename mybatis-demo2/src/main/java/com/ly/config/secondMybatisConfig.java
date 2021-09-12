package com.ly.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ly.mybaties.interceptor.LogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.ly.dao",sqlSessionFactoryRef = "shiroSqlSessionFactory")
public class secondMybatisConfig {

    @Autowired
    private Environment env;

	@Bean("shiroDataSource")
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource(){
		DruidDataSource shiroDataSource = new DruidDataSource();
		return shiroDataSource;
	}
	
	@Bean("shiroSqlSessionFactory")
	//@ConfigurationProperties(prefix="mybatis")
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("shiroDataSource") DataSource shiroDataSource) throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(shiroDataSource);
		//该配置非常的重要，如果不将PageInterceptor设置到SqlSessionFactoryBean中，导致分页失效

		fb.setPlugins(new Interceptor[]{new LogInterceptor()});
		fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
		fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
		return fb;
	}

	@Primary
	@Bean("shiroSqlSessionFactory")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("shiroSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
