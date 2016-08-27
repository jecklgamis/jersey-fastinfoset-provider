package io.jecklgamis;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
import org.glassfish.jersey.jaxb.internal.AbstractJaxbElementProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author Jerrico Gamis <jecklgamis@gmail.com>
 */
@Produces({"application/fastinfoset"})
@Consumes({"application/fastinfoset"})
public class FastInfosetJAXBElementProvider extends AbstractJaxbElementProvider {

    public FastInfosetJAXBElementProvider(@Context Providers providers) {
        super(providers, MediaType.valueOf("application/fastinfoset"));
    }

    @Override
    protected JAXBElement<?> readFrom(Class<?> type, MediaType mediaType, Unmarshaller u, InputStream entityStream) throws JAXBException {
        return u.unmarshal(new StAXDocumentParser(entityStream), type);
    }

    @Override
    protected void writeTo(JAXBElement<?> element, MediaType mediaType, Charset charset, Marshaller m, OutputStream entityStream) throws JAXBException {
        XMLStreamWriter xsw = new StAXDocumentSerializer(entityStream);
        try {
            m.marshal(element, xsw);
            xsw.flush();
        } catch (Exception e) {
            new JAXBException(e);
        }
    }
}
