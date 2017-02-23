package com.jecklgamis.fastinfoset;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.filter.LoggingFilter;

public class ExampleApp extends io.dropwizard.Application<ExampleAppConfig> {
    @Override
    public void run(ExampleAppConfig config, Environment env) throws Exception {
        env.jersey().register(new ExampleResource());
        env.healthChecks().register("default", new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return Result.healthy();
            }
        });
        env.jersey().register(LoggingFilter.class);
        env.jersey().register(FastInfosetJaxbElementProvider.class);
        env.jersey().register(FastInfosetRootElementProvider.class);
    }

    public static void main(String[] args) throws Exception {
        new ExampleApp().run(args);
    }
}
