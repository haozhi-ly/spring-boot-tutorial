package com.ly.shiro.filter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Component
public class PropertiesMappingReader implements urlMappingPermissionReader{

    private final String DEFAULT_FILE_NAME = "shiro_auth.properties";

    private String fileName;

    public PropertiesMappingReader(String fileName) {
        this.fileName = fileName;
    }

    public PropertiesMappingReader() {

    }

    /**
     * 权限配置映射真实的url路径，如下
     *
     *
     *
     * @return
     * @throws IOException
     */
    @Override
    public Map<String, String> getMappingMap() throws IOException {
        Properties properties = new Properties();
        ClassPathResource resource = null;
        if(!StringUtils.isEmpty(fileName)){
            resource = new ClassPathResource(fileName);
        }else{
            resource = new ClassPathResource(DEFAULT_FILE_NAME);
        }
        Map<String,String> urlMappingMap = null;
        try (InputStream is = new BufferedInputStream(new FileInputStream(resource.getFile()))){;
            properties.load(is);
            Iterator<String> iterator = properties.stringPropertyNames().iterator();
            urlMappingMap = new HashMap<>();
            while(iterator.hasNext()){
                String permissionUrl = iterator.next();
                String permissionMappingUrlStr = properties.getProperty(permissionUrl);
                String[] permissionMappingUrlArr = permissionMappingUrlStr.trim().split(",");
                if(permissionMappingUrlArr != null &&
                    permissionMappingUrlArr.length > 0){
                    for(String permissionMappingUrl:permissionMappingUrlArr){
                        urlMappingMap.put(permissionMappingUrl,permissionUrl);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlMappingMap;
    }
}
