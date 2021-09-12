package com.ly.service;


import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class ImportTestService{
    @Autowired
    private ImportTest2Service importTest2Service;

    public void importTest(){
        importTest2Service.importTest();
    }

}
