package com.shi.quartzdemo.utils;

import com.shi.quartzdemo.controller.api.JobRequest;
import com.shi.quartzdemo.job.BaseJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName JobUtils
 * @Author yakun.shi
 * @Date 2019/6/20 13:47
 * @Version 1.0
 **/
@Component
public class JobUtils {

    @Autowired
    private Scheduler scheduler;


    /**
     * @return void
     * @Author yakun.shi
     * @Description //添加任务
     * @Date 2019/6/20 14:23
     * @Param [request]
     **/
    public String addJob(JobRequest request) throws SchedulerException {
        if (request == null) {
            return "fail";
        }
        if (!CronExpression.isValidExpression(request.getCronExpression())) {
            return "fail";
        }
        // 启动调度器
//        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getJobClass(request.getJobName()))
                .withIdentity(request.getJobName(), request.getJobGroupName()).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(request.getCronExpression());
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(request.getJobName(), request.getJobGroupName())
                .withSchedule(scheduleBuilder).build();

        scheduler.scheduleJob(jobDetail, trigger);
        return "success";

    }


    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description // 获取Job状态
     * @Date 2019/6/20 14:17
     * @Param [request]
     **/
    public String getJobState(JobRequest request) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(request.getJobName(), request.getJobGroupName());
        return scheduler.getTriggerState(triggerKey).name();
    }

    /**
     * @return void
     * @Author yakun.shi
     * @Description //暂停所有任务
     * @Date 2019/6/20 14:16
     * @Param []
     **/
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description //暂停任务
     * @Date 2019/6/20 14:16
     * @Param [request]
     **/
    public String pauseJob(JobRequest request) throws SchedulerException {
        JobKey jobKey = new JobKey(request.getJobName(), request.getJobGroupName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.pauseJob(jobKey);
            return "success";
        }

    }

    /**
     * @return void
     * @Author yakun.shi
     * @Description //恢复所有任务
     * @Date 2019/6/20 14:16
     * @Param []
     **/
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }


    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description // 恢复某个任务
     * @Date 2019/6/20 14:16
     * @Param [request]
     **/
    public String resumeJob(JobRequest request) throws SchedulerException {
        JobKey jobKey = new JobKey(request.getJobName(), request.getJobGroupName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.resumeJob(jobKey);
            return "success";
        }
    }


    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description //删除某个任务
     * @Date 2019/6/20 14:16
     * @Param [request]
     **/
    public String deleteJob(JobRequest request) throws SchedulerException {
//
//        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
//        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
//        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        JobKey jobKey = new JobKey(request.getJobName(), request.getJobGroupName());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "jobDetail is null";
        } else if (!scheduler.checkExists(jobKey)) {
            return "jobKey is not exists";
        } else {
            scheduler.deleteJob(jobKey);
            return "success";
        }

    }

    /**
     * @return void
     * @Author yakun.shi
     * @Description //修改定时任务执行时间
     * @Date 2019/6/20 14:22
     * @Param [request]
     **/
    public String jobreschedule(JobRequest request) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(request.getJobName(), request.getJobGroupName());
        // 表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(request.getCronExpression());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        // 按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
        return "success";

    }

    /**
     * @return java.lang.Class
     * @Author yakun.shi
     * @Description //根据前端传进来的job名，来获取对应的job
     * @Date 2019/6/20 14:02
     * @Param [job]
     **/
    private Class getJobClass(String job) {
        Map<String, BaseJob> beanOfType = SpringUtil.getBeanOfType(BaseJob.class);
        String s = lowerFirst(job);
        BaseJob baseJob = beanOfType.get(s);
        return baseJob.getClass();
    }


    /**
     * @return java.lang.Class
     * @Author yakun.shi
     * @Description //获取job类的全限定名
     * @Date 2019/6/20 14:02
     * @Param [job]
     **/
    private String getJobStringClass(String job) {
        Map<String, BaseJob> beanOfType = SpringUtil.getBeanOfType(BaseJob.class);
        String s = lowerFirst(job);
        return beanOfType.get(s).getClass().toString();
    }

    /**
     * @return java.lang.String
     * @Author yakun.shi
     * @Description //首字母小写
     * @Date 2019/6/12 11:12
     * @Param [oldStr]
     **/
    public String lowerFirst(String oldStr) {
        char[] chars = oldStr.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            return oldStr;
        }
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
