package com.shi.quartzdemo.controller.api;

import lombok.Data;

/**
 * @ClassName JobRequest
 * @Author yakun.shi
 * @Date 2019/6/20 14:08
 * @Version 1.0
 **/
@Data
public class JobRequest {
    private String jobName;
    private String jobGroupName;
    private String cronExpression;
}
