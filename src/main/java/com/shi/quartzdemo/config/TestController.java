package com.shi.quartzdemo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ServletWrappingController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: quartz-demo
 * @description: ss
 * @author: yaKun.shi
 * @create: 2019-08-08 11:45
 **/
@Component
public class TestController extends ServletWrappingController {

    public TestController() {
        setServletClass(TestServleet.class);
        setServletName("zuul");
        setSupportedMethods((String[]) null);
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

            // We don't care about the other features of the base class, just want to
            // handle the request
            return super.handleRequestInternal(request, response);

    }
}
