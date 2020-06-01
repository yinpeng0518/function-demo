package com.yp.smssend.web.annotations;

import com.yp.smssend.bean.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@Configuration //表明此类是配置类
//@ComponentScan // 扫描自定义的组件(repository service component controller)
//@PropertySource("classpath:application.yml") // 读取application.properties
//@EnableTransactionManagement //开启事务管理
@EnableSpringStudy
public class AppConfig {

    //....省略配置代码

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = applicationContext.getBean(Student.class);
        System.out.println(student.getName());
    }
}