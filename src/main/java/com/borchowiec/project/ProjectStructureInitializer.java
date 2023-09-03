package com.borchowiec.project;

public interface ProjectStructureInitializer {

    void initializeProjectStructure();

    static ProjectStructureInitializer getInstance() {
        return new ProjectStructureInitializerImpl();
    }
}
