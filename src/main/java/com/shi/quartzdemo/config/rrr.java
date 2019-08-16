package com.shi.quartzdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @program: quartz-demo
 * @description: w
 * @author: yaKun.shi
 * @create: 2019-08-12 17:50
 **/
@Configuration
@AutoConfigureAfter(SchedulerConfig.class)
public class rrr {

    @lalal
    @Autowired
    private List<Test> a = Collections.emptyList();

    @Bean
    public Test get(){
        System.out.println(a);
        return new Test();
    }
}
