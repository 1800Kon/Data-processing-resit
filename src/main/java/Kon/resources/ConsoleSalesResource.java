package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.services.ConsoleSalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Collection;

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    ConsoleSalesService consoleSalesService;

/*    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ConsoleSales save(final ConsoleSalesRequest consoleSalesRequest) {
        if (validateJson(consoleSalesRequest)){
            return consoleSalesService.save(consoleSalesRequest);
        } else {
            return null;
        }
    }*/

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ConsoleSales save(final String raw) {
        if (validateJson(raw)) {
            try {
            ConsoleSalesRequest consoleSalesRequest = objectMapper.readValue(raw, ConsoleSalesRequest.class);
            return consoleSalesService.save(consoleSalesRequest);
                } catch (JsonMappingException e) {
                e.printStackTrace();
                return null;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
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


    public Boolean validateJson(String consoleSalesRequest) {
        try {
            InputStream is = getClass().getResourceAsStream("/data/JsonSchemas/consoleManufacturerSales.json");
            JSONObject rawSchema = new JSONObject(new JSONTokener(is));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(consoleSalesRequest));
            return true;
        } catch (ValidationException e) {
            e.printStackTrace();
            return false;
        }
    }

}
