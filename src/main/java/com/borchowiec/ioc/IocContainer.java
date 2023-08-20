package com.borchowiec.ioc;

import com.borchowiec.terminal.Terminal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IocContainer {
    private static final IocContainer iocContainer = new IocContainer();

    private final Map<Class, Object> beans;

    private IocContainer() {
        this.beans = getBeans();
    }

    public static IocContainer getInstance() {
        return iocContainer;
    }

    private Map<Class, Object> getBeans() {
        Map<Class, Object> beans = new HashMap<>();

        beans.put(Terminal.class, Terminal.getInstance());

        return Collections.synchronizedMap(Collections.unmodifiableMap(beans));
    }

    public <T> T getBean(Class<T> beanClass) {
        if (!beans.containsKey(beanClass)) {
            throw new IocContainerException("Bean for given class " + beanClass + " not found");
        }

        return (T) beans.get(beanClass);
    }
}
