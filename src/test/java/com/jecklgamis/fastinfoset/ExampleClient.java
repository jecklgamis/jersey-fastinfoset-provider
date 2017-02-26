package com.jecklgamis.fastinfoset;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static java.lang.String.format;

public class ExampleClient {
    private static final Logger logger = LoggerFactory.getLogger(ExampleClient.class);

    public static void main(String[] args) throws Exception {
        Response response = client().target("http://127.0.0.1:5050").request()
                .accept("application/fastinfoset").get(Response.class);
        User user = response.readEntity(User.class);
        logger.info(format("%s %s", user.getUsername(), user.getEmail()));
    }

    private static Client client() {
        ClientConfig config = new ClientConfig();
        config.register(LoggingFilter.class);
        config.register(FastInfosetJaxbElementProvider.class);
        config.register(FastInfosetRootElementProvider.class);
        return JerseyClientBuilder.createClient(config);
    }
}
