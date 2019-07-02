package com.ly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@SpringBootApplication
public class SpringApplicationTest extends SpringBootServletInitializer{
	 @Bean
	    public FilterRegistrationBean<?> filterRegistrationBean(){
			FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
			CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter("UTF-8");
			encodingFilter.setForceEncoding(true);
			registrationBean.addUrlPatterns("/*");
			registrationBean.setFilter(encodingFilter);
			//registrationBean.setOrder(order);
			
	    	return registrationBean;
	    }
	
		 @Bean
			public WebMvcConfigurer corsConfigurer(){
				return new WebMvcConfigurer() {
						@Override
						public void addCorsMappings(CorsRegistry registry) {

							registry.addMapping("/*");
						}
				};
	    }
	 
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringApplicationTest.class);
    }

	public static void main(String[] args){
		SpringApplication.run(SpringApplicationTest.class, args);
	}
}	
