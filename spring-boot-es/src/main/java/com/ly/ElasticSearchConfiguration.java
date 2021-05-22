package com.ly;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticSearchConfiguration implements ImportBeanDefinitionRegistrar {
    @Value("${es.hosts}")
    private String hostString;


    @Bean
    public RestHighLevelClient restHighLevelClient(){
        String[] hostStringArrays = hostString.split(",");
        List<HttpHost> httpHostList = new ArrayList<>();
        for (String host:hostStringArrays){
            httpHostList.add(HttpHost.create(host));
        }
        return new RestHighLevelClient(RestClient.builder(
                httpHostList.toArray(new HttpHost[]{})
        ));
    }
}
