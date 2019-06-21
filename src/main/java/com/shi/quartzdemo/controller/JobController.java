package com.shi.quartzdemo.controller;

import com.github.pagehelper.PageInfo;
import com.shi.quartzdemo.controller.api.JobRequest;
import com.shi.quartzdemo.entity.JobAndTrigger;
import com.shi.quartzdemo.service.IJobAndTriggerService;
import com.shi.quartzdemo.utils.JobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;


    @Autowired
    private JobUtils jobUtils;

    private static Logger log = LoggerFactory.getLogger(JobController.class);


    @PostMapping(value = "/addjob")
    public void addjob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        JobRequest request = new JobRequest();
        request.setCronExpression(cronExpression);
        request.setJobGroupName(jobGroupName);
        request.setJobName(jobClassName);
        jobUtils.addJob(request);
    }


    @PostMapping(value = "/pausejob")
    public void pausejob(String jobClassName,String jobGroupName) throws Exception {
        JobRequest request = new JobRequest();
        request.setJobGroupName(jobGroupName);
        request.setJobName(jobClassName);
        jobUtils.pauseJob(request);
    }


    @PostMapping(value = "/resumejob")
    public void resumejob(String jobClassName,String jobGroupName) throws Exception {
        JobRequest request = new JobRequest();
        request.setJobGroupName(jobGroupName);
        request.setJobName(jobClassName);
        jobUtils.resumeJob(request);
    }


    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        JobRequest request = new JobRequest();
        request.setCronExpression(cronExpression);
        request.setJobGroupName(jobGroupName);
        request.setJobName(jobClassName);
        jobUtils.jobreschedule(request);
    }


    @PostMapping(value = "/deletejob")
    public void deletejob(String jobClassName,String jobGroupName) throws Exception {
        JobRequest request = new JobRequest();
        request.setJobGroupName(jobGroupName);
        request.setJobName(jobClassName);
        jobUtils.deleteJob(request);
    }


    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }
}
