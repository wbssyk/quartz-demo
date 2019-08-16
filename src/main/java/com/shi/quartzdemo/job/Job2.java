package com.shi.quartzdemo.job;

import com.shi.quartzdemo.utils.SpringUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * @Author yakun.shi
 * @Description //具体要执行的任务
 * @Date 2019/6/21 10:26
 **/
@Component
public class Job2 implements BaseJob {
  
    private static Logger _log = LoggerFactory.getLogger(Job2.class);

    private int i = 0;


    public void execute(JobExecutionContext context)  
        throws JobExecutionException {
        JdbcTemplate jdbcTemplate = SpringUtil.getBean(JdbcTemplate.class);
        _log.error("New Job执行时间: " + new Date());
//        jdbcTemplate.execute("insert into company (NAME,PEMPLE_NUM) values (99,999)");
    }  
}  