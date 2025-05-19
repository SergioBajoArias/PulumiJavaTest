package com.sergio.controller;

import com.sergio.dto.UpCommand;
import com.sergio.service.DeploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deployment")
public class DeploymentController {

    DeploymentService deploymentService;

    @Autowired
    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @PutMapping("/up")
    public void up(@RequestBody UpCommand upCommand) {
        deploymentService.up(upCommand);
    }

    @PutMapping("/destroy")
    public void destroy() {
        deploymentService.destroy();
    }
}
