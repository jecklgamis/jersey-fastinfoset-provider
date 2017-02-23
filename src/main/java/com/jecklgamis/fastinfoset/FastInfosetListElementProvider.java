package com.jecklgamis.fastinfoset;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
import org.glassfish.jersey.jaxb.internal.AbstractCollectionJaxbProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * @author Jerrico Gamis <jecklgamis@gmail.com>
 */
@Produces({"application/fastinfoset"})
@Consumes({"application/fastinfoset"})
public class FastInfosetListElementProvider extends AbstractCollectionJaxbProvider {

    public FastInfosetListElementProvider(@Context Providers providers) {
        super(providers, MediaType.valueOf("application/fastinfoset"));
    }

    @Override
    public void writeCollection(Class<?> elementType, Collection<?> t, MediaType mediaType, Charset c, Marshaller m, OutputStream entityStream)
            throws JAXBException, IOException {
        XMLStreamWriter xsw = new StAXDocumentSerializer(entityStream);
        String rootElement = getRootElementName(elementType);
        try {
            xsw.writeStartDocument();
            xsw.writeStartElement(rootElement);
            for (Object o : t) m.marshal(o, xsw);
            xsw.writeEndElement();
            xsw.writeEndDocument();
            xsw.flush();
        } catch (XMLStreamException e) {
            e.initCause(new IOException());
        }
    }

    @Override
    protected XMLStreamReader getXMLStreamReader(Class<?> elementType, MediaType mediaType, Unmarshaller unmarshaller, InputStream entityStream)
            throws XMLStreamException {
        StAXDocumentParser parser = new StAXDocumentParser(entityStream);
        parser.setStringInterning(true);
        return parser;
    }
}
