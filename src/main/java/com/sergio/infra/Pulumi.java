package com.sergio.infra;

import com.pulumi.Context;
import com.pulumi.kubernetes.apps.v1.Deployment;
import com.pulumi.kubernetes.apps.v1.DeploymentArgs;
import com.pulumi.kubernetes.apps.v1.inputs.DeploymentSpecArgs;
import com.pulumi.kubernetes.core.v1.ServiceArgs;
import com.pulumi.kubernetes.core.v1.enums.ServiceSpecType;
import com.pulumi.kubernetes.core.v1.inputs.*;
import com.pulumi.kubernetes.core.v1.outputs.ServiceSpec;
import com.pulumi.kubernetes.meta.v1.inputs.LabelSelectorArgs;
import com.pulumi.kubernetes.meta.v1.inputs.ObjectMetaArgs;

import java.util.Map;

public class Pulumi {
    public static void config(Context ctx) {
        var config = ctx.config();
        var numberOfPods = config.getInteger("numberOfPods");
        var labels = Map.of("app", "api");

        var deployment = new Deployment("api", DeploymentArgs.builder()
                .spec(DeploymentSpecArgs.builder()
                        .selector(LabelSelectorArgs.builder()
                                .matchLabels(labels)
                                .build())
                        .replicas(numberOfPods.get())
                        .template(PodTemplateSpecArgs.builder()
                                .metadata(ObjectMetaArgs.builder()
                                        .labels(labels)
                                        .build())
                                .spec(PodSpecArgs.builder()
                                        .containers(ContainerArgs.builder()
                                                .name("api")
                                                .image("vanapagan/test-rest-api")
                                                .ports(ContainerPortArgs.builder()
                                                        .containerPort(80)
                                                        .build())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build());

        var service = new com.pulumi.kubernetes.core.v1.Service("api", ServiceArgs.builder()
                .metadata(ObjectMetaArgs.builder()
                        .labels(deployment.spec().applyValue(spec -> spec.template().metadata().get().labels()))
                        .build())
                .spec(ServiceSpecArgs.builder()
                        .type(ServiceSpecType.NodePort)
                        .selector(labels)
                        .ports(ServicePortArgs.builder()
                                .port(8080)
                                .targetPort(8080)
                                .nodePort(30080)
                                .protocol("TCP")
                                .build())
                        .build())
                .build());

        ctx.export("ip", service.spec().applyValue(ServiceSpec::clusterIP));
    }
}
