package io.jecklgamis;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
import org.glassfish.jersey.jaxb.internal.AbstractRootElementJaxbProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Jerrico Gamis <jecklgamis@gmail.com>
 */
@Produces({"application/fastinfoset"})
@Consumes({"application/fastinfoset"})
public class FastInfosetRootElementProvider extends AbstractRootElementJaxbProvider {

    public FastInfosetRootElementProvider(@Context Providers providers) {
        super(providers, MediaType.valueOf("application/fastinfoset"));
    }

    @Override
    protected Object readFrom(Class<Object> type, MediaType mediaType, Unmarshaller u, InputStream entityStream) throws JAXBException {
        StAXDocumentParser parser = new StAXDocumentParser(entityStream);
        if (mediaType.getClass().isAnnotationPresent(XmlRootElement.class)) {
            return u.unmarshal(parser);
        } else {
            return u.unmarshal(parser, type).getValue();
        }
    }

    @Override
    protected void writeTo(Object t, MediaType mediaType, Charset c, Marshaller m, OutputStream entityStream) throws JAXBException {
        XMLStreamWriter xsw = new StAXDocumentSerializer(entityStream);
        m.marshal(t, xsw);
        try {
            xsw.flush();
        } catch (XMLStreamException e) {
            throw new JAXBException(e);
        }
    }
}
