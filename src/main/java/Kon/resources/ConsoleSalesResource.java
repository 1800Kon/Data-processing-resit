package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.repositories.ConsoleSalesRepository;
import Kon.services.ConsoleSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {

    @Autowired
    ConsoleSalesService consoleSalesService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ConsoleSales save(final ConsoleSalesRequest consoleSalesRequest) {
        return consoleSalesService.save(consoleSalesRequest);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<ConsoleSales> getAll() {
        return consoleSalesService.getAll();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ConsoleSales update(@PathParam("id") final Integer id, final ConsoleSalesRequest consoleSalesRequest) {
        return consoleSalesService.update(id, consoleSalesRequest);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") final Integer id) {
        consoleSalesService.delete(id);
    }
}
