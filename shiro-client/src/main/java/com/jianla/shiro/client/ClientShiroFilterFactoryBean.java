package com.jianla.shiro.client;

import javax.servlet.Filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * 设置客户端过滤器（分号分隔）
 * 用于配置shiroFilter
 * 
 * @author dimhat
 * @date 2015年12月6日 下午2:36:37
 * @version 1.0
 */
public class ClientShiroFilterFactoryBean extends ShiroFilterFactoryBean implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setFiltersStr(String filters) {
        if (StringUtils.isEmpty(filters)) {
            return;
        }
        String[] filterArray = filters.split(";");
        for (String filter : filterArray) {
            String[] o = filter.split("=");
            getFilters().put(o[0], (Filter) applicationContext.getBean(o[1]));
        }
    }

    public void setFilterChainDefinitionsStr(String filterChainDefinitions) {
        if (StringUtils.isEmpty(filterChainDefinitions)) {
            return;
        }
        String[] chainDefinitionsArray = filterChainDefinitions.split(";");
        for (String filter : chainDefinitionsArray) {
            String[] o = filter.split("=");
            getFilterChainDefinitionMap().put(o[0], o[1]);
        }
    }
}
