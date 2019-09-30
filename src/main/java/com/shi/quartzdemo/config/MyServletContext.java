package com.shi.quartzdemo.config;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @program: quartz-demo
 * @description: servlcetlistener
 * @author: yaKun.shi
 * @create: 2019-08-19 17:52
 **/
@WebListener
public class MyServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ssssssssss");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
