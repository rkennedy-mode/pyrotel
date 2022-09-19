package com.mode.ryankennedy.pyrotel;

import com.mode.ryankennedy.pyrotel.health.HeartbeatHealthCheck;
import com.mode.ryankennedy.pyrotel.resources.GreetingsResource;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Pyrotel extends Application<PyrotelConfiguration> {
    public static void main(String[] args) throws Exception {
        new Pyrotel().run(args);
    }

    @Override
    public void initialize(Bootstrap<PyrotelConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        new ResourceConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(true, true))
        );
    }

    @Override
    public void run(PyrotelConfiguration configuration, Environment environment) {
        environment.healthChecks().register("heartbeat", new HeartbeatHealthCheck());

        environment.jersey().register(new GreetingsResource());
    }
}
