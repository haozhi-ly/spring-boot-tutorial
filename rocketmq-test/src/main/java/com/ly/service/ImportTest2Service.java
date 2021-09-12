package com.ly.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ImportTest2Service {

    private Logger logger = LoggerFactory.getLogger(ImportTest2Service.class);

    public void importTest(){
        System.out.println("i'm a importTest2 bean");
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void task(){
        byte[] bytes = new byte[1024*1024*10];

        logger.info("test gc,time:{},bytes.length:{}", LocalDateTime.now(),bytes.length);
    }

}
