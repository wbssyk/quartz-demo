package com.shi.quartzdemo.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * @program: quartz-demo
 * @description: ss
 * @author: yaKun.shi
 * @create: 2019-08-08 11:47
 **/
public class TestServleet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("调用自定义的servlet init......................................");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("调用自定义的servlet service方法......................................");
        String s = req.getRequestURL().toString();
        String requestURI = req.getRequestURI();
        if (requestURI.startsWith("/job")) {

            URL url = new URL("http://localhost:7101/cloud-service/banner/list");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int read = inputStream.read(buffer);
            resp.setCharacterEncoding("utf-8");
            ServletOutputStream outputStream = resp.getOutputStream();
            while (read!=-1){
                outputStream.write(buffer,0,read);
                read = inputStream.read(buffer);
            }
            outputStream.close();
            inputStream.close();
        }
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        Test test = (Test) factory.getBean("test");
        resp.getWriter().write(test.getAa());
//        super.service(req, resp);
    }
}
