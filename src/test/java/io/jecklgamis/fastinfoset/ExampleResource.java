package io.jecklgamis.fastinfoset;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Path("/")
@Produces({"application/xml", "application/fastinfoset"})
@Consumes({"application/xml", "application/fastinfoset"})
public class ExampleResource {

    @Path("/rootElement")
    @POST
    public Response rootElement(User entity) {
        return Response.ok().entity(entity).build();
    }

    @POST
    @Path("/jaxbElement")
    public Response jaxElement(User2 entity) {
        return Response.ok().entity(new JAXBElement<>(QName.valueOf(User2.class.getSimpleName()), User2.class, entity)).build();
    }

}


