package io.jecklgamis.fastinfoset;

import io.dropwizard.testing.junit.DropwizardAppRule;
import io.jecklgamis.FastInfosetJAXBElementProvider;
import io.jecklgamis.FastInfosetRootElementProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static java.lang.String.format;
import static javax.ws.rs.client.Entity.entity;
import static org.junit.Assert.assertEquals;

public class ExampleAppIntTest {
    @ClassRule
    public static final DropwizardAppRule<ExampleAppConfig> RULE = new DropwizardAppRule(ExampleApp.class, resourceFilePath("config.yml"));

    @Test
    public void shouldRespondToRootElementRequest() {
        Response response = client().target(format("http://127.0.0.1:%d/rootElement", RULE.getLocalPort()))
                .request().accept("application/fastinfoset")
                .post(entity(new User(), "application/fastinfoset"), Response.class);
        assertEquals("application/fastinfoset", response.getHeaders().getFirst("Content-Type"));
        User entity = response.readEntity(User.class);
        assertEquals("user", entity.getUsername());
        assertEquals("user@example.com", entity.getEmail());
    }

    @Test
    public void shouldRespondToJAXBElementRequest() {
        Response response = client().target(format("http://127.0.0.1:%d/jaxbElement", RULE.getLocalPort()))
                .request().accept("application/fastinfoset")
                .post(entity(new JAXBElement<>(new QName("", "User2"), User2.class, new User2()), "application/fastinfoset"),
                        Response.class);
        assertEquals(200, response.getStatus());
        assertEquals("application/fastinfoset", response.getHeaders().getFirst("Content-Type"));
        User2 entity = response.readEntity(User2.class);
        assertEquals("user", entity.getUsername());
        assertEquals("user@example.com", entity.getEmail());
    }

    private Client client() {
        ClientConfig config = new ClientConfig();
        config.register(FastInfosetJAXBElementProvider.class);
        config.register(FastInfosetRootElementProvider.class);
        config.property(ClientProperties.CONNECT_TIMEOUT, 5000);
        config.property(ClientProperties.READ_TIMEOUT, 15000);
        return JerseyClientBuilder.createClient(config);
    }
}

