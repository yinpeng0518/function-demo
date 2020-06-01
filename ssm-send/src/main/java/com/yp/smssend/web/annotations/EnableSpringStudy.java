package com.yp.smssend.web.annotations;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(SpringStudySelector.class)
public @interface EnableSpringStudy {

}