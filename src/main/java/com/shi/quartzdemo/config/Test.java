package com.shi.quartzdemo.config;




import com.github.pagehelper.PageInfo;
import com.netflix.client.ClientFactory;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ConfigurationBasedServerList;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import com.netflix.niws.client.http.RestClient;
import com.shi.quartzdemo.entity.JobAndTrigger;
import com.shi.quartzdemo.service.IJobAndTriggerService;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import static com.netflix.client.config.CommonClientConfigKey.ListOfServers;

/**
 * @program: quartz-demo
 * @description: s
 * @author: yaKun.shi
 * @create: 2019-08-07 15:31
 **/
public class Test  extends TestDi{


//    public static void main(String[] args) throws IOException {
//        URL url = new URL("http://192.168.1.200:40000/2659e3d1a1364ad483c58d13efe63c41.jpg");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        InputStream inputStream = connection.getInputStream();
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//        byte[] buffer = new byte[1024];
//        int read = bufferedInputStream.read(buffer);
//        File file = new File("D:\\aa\\a.jpg");
//        FileOutputStream fileOutputStream = new FileOutputStream(file);
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//        while (read!=-1){
//            bufferedOutputStream.write(buffer,0,read);
//            read = bufferedInputStream.read(buffer);
//        }
//        bufferedOutputStream.close();
//        fileOutputStream.close();
//
//
//        bufferedInputStream.close();
//        inputStream.close();
////        IJobAndTriggerService iJobAndTriggerService = (pageNum, pageSize) -> null;
//    }

    public static void main(String[] args) throws Exception{

        ConfigurationManager.loadPropertiesFromResources("client.properties");

//        System.out.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers"));

        RestClient client = (RestClient) ClientFactory.getNamedClient("sample-client");
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build();
        for (int i = 0; i < 20; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.out.println("Status code for " + response.getRequestedURI() + "  :" + response.getStatus());
        }
        ZoneAwareLoadBalancer lb = (ZoneAwareLoadBalancer) client.getLoadBalancer();
        System.out.println(lb.getLoadBalancerStats());

        System.out.println("changing servers ...");
        Thread.sleep(3000);
        for (int i = 0; i < 20; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            System.out.println("Status code for " + response.getRequestedURI() + "  : " + response.getStatus());
        }
        System.out.println(lb.getLoadBalancerStats()); // 6

    }
}
