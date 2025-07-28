package com.clevermoe.workflow.controller;

import com.clevermoe.workflow.service.WorkflowOrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowOrchestratorService workflowService;

    @GetMapping("/run")
    public ResponseEntity<Object> runWorkflow() {
        Object result = workflowService.runWorkflow();
        return ResponseEntity.ok(result);

    }
}
