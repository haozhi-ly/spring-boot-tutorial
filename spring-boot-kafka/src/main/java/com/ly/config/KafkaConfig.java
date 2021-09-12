package com.ly.config;

//import org.mybatis.spring.annotation.MapperScan;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

//@Configuration
//@Component
public class KafkaConfig {

    @KafkaListener( groupId = "test1",topics = {"my-topic"})
    public void listen(List<ConsumerRecord<String, String>> list, Acknowledgment ack){
        for (ConsumerRecord<String,String> record: list){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                ack.acknowledge();
                //ack.
        }

        //for (ConsumerRecord<String,String> record: list){
            //System.out.printf("offset = %d, key = %s, value = %s%n", list.offset(), list.key(), list.value());
           // ack.acknowledge();
            //ack.
        //}
    }







    /*@Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }*/



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
