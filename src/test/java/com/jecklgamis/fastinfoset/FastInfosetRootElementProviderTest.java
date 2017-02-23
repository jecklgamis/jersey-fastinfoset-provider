package com.jecklgamis.fastinfoset;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;

import static java.nio.charset.Charset.forName;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class FastInfosetRootElementProviderTest {

    @Test
    public void testRoundTripSerialization() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(User.class);

        User user = new User("me", "me@exampe.com");
        MediaType mediaType = MediaType.valueOf("application/fastinfoset");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastInfosetRootElementProvider provider = new FastInfosetRootElementProvider(mock(Providers.class));
        provider.writeTo(user, mediaType, forName("UTF-8"), jaxbContext.createMarshaller(), outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Class<Object> type = (Class<Object>) Class.forName(User.class.getName());
        User deserializedUser = (User) provider.readFrom(type, mediaType, jaxbContext.createUnmarshaller(), inputStream);

        assertTrue(deserializedUser.getUsername().equals("me"));
        assertTrue(deserializedUser.getEmail().equals("me@example.com"));
    }

}

