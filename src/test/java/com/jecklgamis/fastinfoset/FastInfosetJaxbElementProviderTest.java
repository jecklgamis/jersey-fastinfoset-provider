package com.jecklgamis.fastinfoset;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static java.nio.charset.Charset.forName;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class FastInfosetJaxbElementProviderTest {

    @Test
    public void testRoundTripSerialization() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(User2.class);
        MediaType mediaType = MediaType.valueOf("application/fastinfoset");

        JAXBElement<User2> element = new JAXBElement(new QName("", "User2"), User2.class, new User2("me", "me@example.com"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastInfosetJaxbElementProvider provider = new FastInfosetJaxbElementProvider(mock(Providers.class));
        provider.writeTo(element, mediaType, forName("UTF-8"), jaxbContext.createMarshaller(), outputStream);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        Class<?> type = Class.forName(User2.class.getName());
        JAXBElement<User2> deserializedUser = (JAXBElement<User2>) provider.readFrom(type, mediaType, jaxbContext.createUnmarshaller(), inputStream);

        assertTrue(deserializedUser.getValue().getUsername().equals("me"));
        assertTrue(deserializedUser.getValue().getEmail().equals("me@example.com"));
    }

}

