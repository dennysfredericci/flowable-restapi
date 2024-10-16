package com.devoctans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/process")
public class ExampleResource {
    
    private final ProcessEngine processEngine;
    
    public ExampleResource(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        
        RuntimeService runtimeService = processEngine.getRuntimeService();
        
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "Dennys Fredericci");
        variables.put("nrOfHolidays", 32);
        variables.put("description", "Yeah, I need holidays");
        
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        
        processEngine.getTaskService().createTaskQuery().taskCandidateGroup("managers").list();
        
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }

        
        return "Hello from Quarkus REST";
    }
    
}
