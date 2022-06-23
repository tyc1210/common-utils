package com.tyc.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2022-06-23 11:02:13
 */
@Component
public class BeanUtil implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanUtil.beanFactory = beanFactory;
    }

    public static <T> T getBean(String beanName){
        return (T) beanFactory.getBean(beanName);
    }
}
