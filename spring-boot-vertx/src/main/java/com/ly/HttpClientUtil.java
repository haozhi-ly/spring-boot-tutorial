package com.ly;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
    private  CloseableHttpClient closeableHttpClient = null;
    private  final Object lock = new Object();

    private  int maxTotal;
    private  int maxPerRoute;
    private  int maxRoute;
    private  int timeOut;

    public HttpClientUtil(int maxTotal, int maxPerRoute, int maxRoute, int timeOut) {
        this.maxTotal = maxTotal;
        this.maxPerRoute = maxPerRoute;
        this.maxRoute = maxRoute;
        this.timeOut = timeOut;
    }

    public HttpClientUtil() {
    }

    public  RequestConfig getRequestConfig(int timeOut){
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut)
                .setConnectionRequestTimeout(timeOut).build();
        return config;
    }

    public  CloseableHttpClient getHttpClient(){
        if(closeableHttpClient == null){
            synchronized (lock){
                if(closeableHttpClient == null){
                    closeableHttpClient = createHttpClient(maxTotal,maxPerRoute,maxRoute);
                }
            }
        }
        return closeableHttpClient;
    }

    private  CloseableHttpClient createHttpClient(int maxTotal, int maxPerRoute, int maxRoute) {
        ConnectionSocketFactory connectionSocketFactory = PlainConnectionSocketFactory
                .getSocketFactory();
        LayeredConnectionSocketFactory layeredConnectionSocketFactory = SSLConnectionSocketFactory
                .getSocketFactory();
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        clientConnectionManager.setMaxTotal(maxTotal);
        clientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(clientConnectionManager)
        .build();

        return closeableHttpClient;
    }

    public  String postUrlEncodeForm(String url, Map<String,Object> params,
                                           Map<String,Object> headers) throws IOException {
        
        HttpPost post = new HttpPost(url);
        post.setConfig(getRequestConfig(timeOut));
        List<NameValuePair> list = new ArrayList<>();
        params.forEach((key,value)->{
            list.add(new BasicNameValuePair(key,value.toString()));
        });
        post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response=getHttpClient().execute(post,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity,"UTF-8");
            EntityUtils.consume(entity);
        } finally {
            if(response != null){
                response.close();
            }
        }
        return result;
    }
    
    
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClientUtil clientUtil = new HttpClientUtil(5,5,5,120000);
        Map<String,Object> map = new HashMap<>();
        map.put("name","ly");
        String result = clientUtil.postUrlEncodeForm("http://localhost:8080/hello1",map
        ,null);
        System.out.println(result);
        while (true){
            Thread.sleep(5000);
            clientUtil.getHttpClient();
        }
    }
    

}
