package com.u8d75.core.spring;

import com.u8d75.core.constant.EnvironmentEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringContextHolder
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;
    private static EnvironmentEnum environment;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * setApplicationContext
     *
     * @param context context
     * @throws BeansException BeansException
     */
    @SuppressWarnings("squid:S2696")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextHolder.context = context;
        environment = EnvironmentEnum.getByCode(context.getEnvironment().getActiveProfiles()[0]);
    }

    /**
     * getBean
     *
     * @param name name
     * @param <T>  <T>
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    /**
     * getBean
     *
     * @param requiredType requiredType
     * @param <T>          <T>
     * @return <T>
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    public static EnvironmentEnum getEnvironment() {
        return environment;
    }

}
