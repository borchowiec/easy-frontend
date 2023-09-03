package com.borchowiec.tag.handler;

import com.borchowiec.ioc.IocContainer;
import com.borchowiec.project.GlobalVariables;

class GlobalHandler implements TagHandler {
    private final GlobalVariables globalVariables;

    public GlobalHandler() {
        IocContainer iocContainer = IocContainer.getInstance();
        this.globalVariables = iocContainer.getBean(GlobalVariables.class);
    }

    @Override
    public String handle(String wholeTag,
                         String tagName,
                         String tagValue) {
        return globalVariables.getGlobalVariable(tagValue);
    }
}
