package com.sergio.service;

import com.sergio.dto.UpCommand;

public interface DeploymentService {
    void up(UpCommand upCommand);

    void destroy();
}
