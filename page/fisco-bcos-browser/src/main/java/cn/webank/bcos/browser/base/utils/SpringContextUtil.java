package cn.webank.bcos.browser.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	    if(applicationContext == null)
	    {
	        SpringContextUtil.applicationContext = arg0;
	    }
	}

	public static ApplicationContext getApplicationContext() {
		return SpringContextUtil.applicationContext;
	}

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
