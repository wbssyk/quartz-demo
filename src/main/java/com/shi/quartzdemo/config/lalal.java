package com.shi.quartzdemo.config;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * @program: quartz-demo
 * @description: kk
 * @author: yaKun.shi
 * @create: 2019-08-12 17:46
 **/

@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//@Qualifier
public @interface lalal {
}
