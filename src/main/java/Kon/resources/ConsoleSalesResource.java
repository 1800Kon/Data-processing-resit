package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.services.ConsoleSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {

    @Autowired
    ConsoleSalesService consoleSalesService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ConsoleSales save(final ConsoleSalesRequest consoleSalesRequest) {
        return consoleSalesService.save(consoleSalesRequest);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<ConsoleSales> getAll() {
        return consoleSalesService.getAll();
    }
}