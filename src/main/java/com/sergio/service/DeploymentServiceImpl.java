package com.sergio.service;

import com.pulumi.Context;
import com.pulumi.automation.AutomationException;
import com.pulumi.automation.ConfigValue;
import com.pulumi.automation.LocalWorkspace;
import com.pulumi.automation.WorkspaceStack;
import com.pulumi.core.Output;
import com.pulumi.kubernetes.apps.v1.Deployment;
import com.pulumi.kubernetes.apps.v1.DeploymentArgs;
import com.pulumi.kubernetes.apps.v1.inputs.DeploymentSpecArgs;
import com.pulumi.kubernetes.core.v1.ServiceArgs;
import com.pulumi.kubernetes.core.v1.enums.ServiceSpecType;
import com.pulumi.kubernetes.core.v1.inputs.*;
import com.pulumi.kubernetes.core.v1.outputs.ServiceSpec;
import com.pulumi.kubernetes.meta.v1.inputs.LabelSelectorArgs;
import com.pulumi.kubernetes.meta.v1.inputs.ObjectMetaArgs;
import com.sergio.dto.UpCommand;
import com.sergio.infra.Pulumi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

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
