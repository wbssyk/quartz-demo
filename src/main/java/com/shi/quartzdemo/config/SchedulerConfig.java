package com.shi.quartzdemo.config;

import com.netflix.config.ConfigurationManager;
import com.shi.quartzdemo.job.BaseJob;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Configuration
public class SchedulerConfig {

    /**
     * SchedulerFactoryBean这个类的真正作用提供了对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     * org.quartz.Scheduler: 调度器。所有的调度都是由它控制。
     */

    @Bean(name="schedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //可选,QuartzScheduler启动时更新己存在的Job,这样就不用每次修改targetObject后删除qrtz_job_details表对应记录
        factory.setQuartzProperties(quartzProperties());
        System.out.println("222222222222222222222");
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
  
    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
       return new QuartzInitializerListener();
    }
    
    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="scheduler")
    public Scheduler scheduler() throws IOException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        return scheduler;
    }


    @Configuration
    class MyServletConfig{
        @Bean
        @ConditionalOnMissingBean(name = "zuulServlet")
        public ServletRegistrationBean zuulServlet() {
            ServletRegistrationBean<TestServleet> servlet = new ServletRegistrationBean<>(new TestServleet(),
                    "/*");
            // The whole point of exposing this servlet is to provide a route that doesn't
            // buffer requests.
            servlet.addInitParameter("buffer-requests", "false");
            System.out.println("111111111111111111111111");
            return servlet;
        }

        @Bean("test")
        @lalal
        public Test a(List<BaseJob> baseJobs){
            System.out.println(baseJobs);
            Test test = new Test();
            test.setAa("3232323");
            return test;
        }


        @lalal
        @Bean
        public Test aa(List<BaseJob> baseJobs){
            System.out.println(baseJobs);
            Test test = new Test();
            test.setAa("777");
            return test;
        }
    }

}
