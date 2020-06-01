package com.yp.smssend.web.annotations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Slf4j
public class SpringStudySelector implements ImportSelector, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        importingClassMetadata.getAnnotationTypes().forEach(n->log.debug("【annotations】"+n));
        log.debug("【beanFactory】"+beanFactory.toString());
        return new String[]{MyConfig.class.getName()};
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }
}