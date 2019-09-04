package com.ly.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ly.dao")
public class SpringConfig {
	/*@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	
	
	@Autowired
    private Environment env;


   
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		return dataSource;
	}*/
	
	/* @Bean
	    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
	        fb.setDataSource(dataSource);
	        //该配置非常的重要，如果不将PageInterceptor设置到SqlSessionFactoryBean中，导致分页失效
	        //fb.setPlugins(new Interceptor[]{pageInterceptor});
	        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
	        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
	        return fb.getObject();
	    }*/
}
