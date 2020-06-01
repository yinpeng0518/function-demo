package com.yp.smssend.web.annotations;

import com.yp.smssend.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Student studentBean() {
        Student student = new Student();
        student.setId("19");
        student.setName("admin");
        return student;
    }
}