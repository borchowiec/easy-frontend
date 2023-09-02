package com.borchowiec.ioc;

import com.borchowiec.js.JsEngine;
import com.borchowiec.project.FileWatcher;
import com.borchowiec.project.GlobalVariables;
import com.borchowiec.project.ProjectStructureInitializer;
import com.borchowiec.project.SourceCompiler;
import com.borchowiec.project.SourceRecompilationAwaiter;
import com.borchowiec.tag.TagService;
import com.borchowiec.tag.handler.TagHandlerFactory;
import com.borchowiec.terminal.Terminal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IocContainer {
    private static IocContainer iocContainer;

    private Map<Class, Object> beans;

    private IocContainer() {}

    public static IocContainer getInstance() {
        if (iocContainer == null) {
            iocContainer = new IocContainer();
            iocContainer.init();
        }
        return iocContainer;
    }

    private void init() {
        this.beans = getBeans();
    }

    private Map<Class, Object> getBeans() {
        this.beans = new HashMap<>();

        this.beans.put(JsEngine.class, JsEngine.getInstance());
        this.beans.put(GlobalVariables.class, GlobalVariables.getInstance());
        this.beans.put(TagHandlerFactory.class, TagHandlerFactory.getInstance());
        this.beans.put(Terminal.class, Terminal.getInstance());
        this.beans.put(ProjectStructureInitializer.class, ProjectStructureInitializer.getInstance());
        this.beans.put(SourceCompiler.class, SourceCompiler.getInstance());
        this.beans.put(FileWatcher.class, FileWatcher.getInstance());
        this.beans.put(TagService.class, TagService.getInstance());
        this.beans.put(SourceRecompilationAwaiter.class, SourceRecompilationAwaiter.getInstance());

        return Collections.synchronizedMap(Collections.unmodifiableMap(beans));
    }

    public <T> T getBean(Class<T> beanClass) {
        if (!beans.containsKey(beanClass)) {
            throw new IocContainerException("Bean for given class " + beanClass + " not found");
        }

        return (T) beans.get(beanClass);
    }
}
