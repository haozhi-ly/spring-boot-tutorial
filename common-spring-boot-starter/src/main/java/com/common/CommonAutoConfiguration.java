package com.common;

import com.common.entity.User;
import com.common.service.UserService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "com.common.user",value = "enable",matchIfMissing = true)
@EnableConfigurationProperties(User.class)
@AutoConfigureAfter
public class CommonAutoConfiguration {

    @Bean
    UserService userService(User user){
        UserService userService = new UserService();
        userService.setUser(user);
        return userService;
    }


}
