package com.sergio.service;

import com.pulumi.automation.AutomationException;
import com.pulumi.automation.ConfigValue;
import com.pulumi.automation.LocalWorkspace;
import com.pulumi.automation.WorkspaceStack;
import com.sergio.dto.UpCommand;
import com.sergio.infra.Pulumi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeploymentServiceImpl implements DeploymentService {

    private String projectName;

    private String stackName;

    private WorkspaceStack stack;

    public DeploymentServiceImpl(@Value("${pulumi.projectName}") String projectName, @Value("${pulumi.stackName}") String stackName) {
        this.projectName = projectName;
        this.stackName = stackName;

        try {
            stack = LocalWorkspace.createOrSelectStack(projectName, stackName, Pulumi::config);
        } catch (Exception e) {
            log.error("Error while connecting to Pulumi stack", e);
            throw new RuntimeException("Not connected to Pulumi stack");
        }

    }

    @Override
    public void up(UpCommand upCommand) {
        try {
            log.info("Updating Pulumi stack to work with {} pods", upCommand.getNumberOfPods());
            stack.setConfig("numberOfPods", new ConfigValue(String.valueOf(upCommand.getNumberOfPods()), false));
            stack.setConfig("isMinikube", new ConfigValue(Boolean.TRUE.toString(), false));
            stack.up();
            log.info("Pulumi stack updated");
        } catch (AutomationException e) {
            log.error("Error while updating Pulumi stack");
        }
    }

    @Override
    public void destroy() {
        try {
            log.info("Destroying Pulumi stack");
            stack.destroy();
            log.info("Pulumi stack destroyed");
        } catch (AutomationException e) {
            log.error("Error while destroying Pulumi stack");
        }
    }
}
