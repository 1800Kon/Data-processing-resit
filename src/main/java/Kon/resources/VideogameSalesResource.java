package Kon.resources;

import Kon.models.videogameSales.client.VideogameSales;
import Kon.models.videogameSales.client.VideogameSalesRequest;
import Kon.services.VideogameSalesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.xml.XmlMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Path("/videogameSales")
@Component
public class VideogameSalesResource {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    VideogameSalesService videogameSalesService;

    @Autowired
    HttpServletRequest request;

    @GET
    @Path("/getall")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() {
        Collection<VideogameSales> collection = videogameSalesService.getAll();
        GenericEntity<Collection<VideogameSales>> entity = new GenericEntity<>(collection) {
        };
        return Response.ok(entity).build();
    }

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response save(final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                try {
                    VideogameSalesRequest videogameSalesRequest = objectMapper.readValue(raw, VideogameSalesRequest.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    ow.writeValueAsString(videogameSalesService.save(videogameSalesRequest));
                    return Response.status(201, "[{\"Response\": Successfully added}]").build();
                } catch (Exception e) {
                    return Response.status(400, "[{\"Response\": "+ e +"}]").build();
                }
            } else {
                return Response.status(400, "[{\"Response\": "+ validated +"}]").build();
            }
        } else if (request.getHeader("Accept").equals("application/xml") && request.getHeader("Content-Type").equals("application/xml")) {
            String validated = validateXml(raw);
            if (validated.equals("true")) {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    VideogameSalesRequest videogameSalesRequest = xmlMapper.readValue(raw, VideogameSalesRequest.class);
                    videogameSalesService.save(videogameSalesRequest);
                    return Response.status(201, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+"Success"+"</response></videoGameSales>").build();
                } catch (Exception e) {
                    return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+e+"</response></videoGameSales>").build();
                }
            } else {
                return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+validated+"</response></videoGameSales>").build();
            }
        } else {
            return Response.status(400, "Please use JSON or XML.").build();
        }
    }

    @PUT
    @Path("/put/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response update(@PathParam("id") final Integer id, final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                VideogameSalesRequest videogameSalesRequest;
                try {
                    videogameSalesRequest = objectMapper.readValue(raw, VideogameSalesRequest.class);
                } catch (Exception e) {
                    return Response.status(400, "[{\"Response\": "+e+"}]").build();
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    ow.writeValueAsString(videogameSalesService.update(id, videogameSalesRequest));
                } catch (Exception e) {
                    return Response.status(400, "[{\"Response\": "+e+"}]").build();
                }
                return Response.status(200, "[{\"Response\": Successfully updated}]").build();
            } else {
                return Response.status(400, "[{\"Response\": "+validated+"}]").build();
            }
        } else if (request.getHeader("Accept").equals("application/xml") && request.getHeader("Content-Type").equals("application/xml")) {
            String validated = validateXml(raw);
            if (validated.equals("true")) {
                XmlMapper xmlMapper = new XmlMapper();
                VideogameSalesRequest videogameSalesRequest;
                try {
                    videogameSalesRequest = xmlMapper.readValue(raw, VideogameSalesRequest.class);
                } catch (IOException e) {
                    return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+e+"</response></videoGameSales>").build();
                }
                try {
                videogameSalesService.update(id, videogameSalesRequest);
                }catch (Exception e) {
                    return Response.status(404, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+e+"</response></videoGameSales>").build();
                }
                return Response.status(200, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>Successfully updated</response></videoGameSales>").build();
            } else {
                return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><videoGameSales><response>"+validated+"</response></videoGameSales>").build();
            }
        } else {
            return Response.status(400, "Please use JSON or XML").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") final Integer id) {
        try {
            videogameSalesService.delete(id);
            return Response.status(200, "Successfully deleted").build();
        } catch (Exception e) {
            return Response.status(204, e.toString()).build();
        }
    }


    public String validateJson(String gameSalesRequest) {
        try {
            InputStream is = getClass().getResourceAsStream("/data/JsonSchemas/videoGameSalesWithRatings.json");
            JSONObject rawSchema = new JSONObject(new JSONTokener(is));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(gameSalesRequest));
            return "true";
        } catch (Exception e) {
            return e.toString();
        }
    }

    public String validateXml(String gameSalesRequest) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            javax.xml.validation.Schema schema = schemaFactory.newSchema(new StreamSource(this.getClass().getResourceAsStream("/data/XmlSchemas/videoGameSalesWithRatings.xsd")));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(gameSalesRequest.getBytes())));
            return "true";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
