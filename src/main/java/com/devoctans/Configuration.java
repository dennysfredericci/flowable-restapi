package com.devoctans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;

@ApplicationScoped
class Configuration {
    
    @Produces
    @Singleton
    public ProcessEngineConfiguration processEngineConfiguration() {
        return new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
    }
    
    @Produces
    @Singleton
    public ProcessEngine processEngine(ProcessEngineConfiguration processEngineConfiguration) {
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        deployProcess(processEngine);
        return processEngine;
    }
    
    public Deployment deployProcess(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        return repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
    }
    
    
}
