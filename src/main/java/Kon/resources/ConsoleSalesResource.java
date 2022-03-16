package Kon.resources;

import Kon.services.ConsoleSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {
    @Autowired
    ConsoleSalesService consoleSalesService;

    @POST
    
}
