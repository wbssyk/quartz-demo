package com.shi.quartzdemo.service;

import com.github.pagehelper.PageInfo;
import com.shi.quartzdemo.entity.JobAndTrigger;

@FunctionalInterface
public interface IJobAndTriggerService {
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
