package com.shi.quartzdemo.config;

import com.shi.quartzdemo.job.Job1;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName QuartzConfig
 * @Author yakun.shi
 * @Date 2019/6/20 8:43
 * @Version 1.0
 **/
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testQuartz1() {
        return JobBuilder.newJob(Job1.class).withIdentity("testTask1").storeDurably().build();
    }

    @Bean
    public Trigger testQuartzTrigger1() {
        //5秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger().forJob(testQuartz1())
                .withIdentity("testTask1")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
