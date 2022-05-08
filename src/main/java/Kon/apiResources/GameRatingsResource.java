package Kon.apiResources;

import Kon.models.gameRatings.client.GameRatings;
import Kon.models.gameRatings.client.GameRatingsRequest;
import Kon.services.GameRatingsService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

@Path("/gameRatings")
@Component
public class GameRatingsResource {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    GameRatingsService gameRatingsService;

    @Autowired
    HttpServletRequest request;

    @GET
    @Path("/getall")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAll() {
        // Wrapping the collection in a generic entity so the parsing to XML is possible
        Collection<GameRatings> collection = gameRatingsService.getAll();
        GenericEntity<Collection<GameRatings>> entity = new GenericEntity<>(collection) {};
        return Response.ok(entity).build();
    }

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response save(final String raw) {
        // Check the headers to see which type of result to return
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            // Check if the validation is correct for the body
            if (validated.equals("true")) {
                try {
                    GameRatingsRequest gameRatingsRequest = objectMapper.readValue(raw, GameRatingsRequest.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    ow.writeValueAsString(gameRatingsService.save(gameRatingsRequest));
                    return Response.status(201, "[{\"Response\": Successfully added}]").build();
                } catch (JsonProcessingException e) {
                    // Catch any errors in the saving process
                    return Response.status(400, "[{\"Response\": "+ e +"}]").build();
                }
            } else {
                return Response.status(400, "[{\"Response\": "+ validated +"}]").build();
            }
        } else if (request.getHeader("Accept").equals("application/xml") && request.getHeader("Content-Type").equals("application/xml")) {
            String validated = validateXml(raw);
            if (validated.equals("true")) {
                try {
                    // Create the xml mapper which converts the XML body to save
                    XmlMapper xmlMapper = new XmlMapper();
                    GameRatingsRequest gameRatingsRequest = xmlMapper.readValue(raw, GameRatingsRequest.class);
                    gameRatingsService.save(gameRatingsRequest);
                    return Response.status(201, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+"Success"+"</response></gameRatings>").build();
                } catch (IOException e) {
                    return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+ e +"</response></gameRatings>").build();

                }
            } else {
                return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+ validated +"</response></gameRatings>").build();

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
        // Check the headers of the request
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                GameRatingsRequest gameRatingsRequest;
                try {
                    gameRatingsRequest = objectMapper.readValue(raw, GameRatingsRequest.class);
                } catch (JsonProcessingException e) {
                    return Response.status(400, "[{\"Response\": "+e+"}]").build();
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    ow.writeValueAsString(gameRatingsService.update(id, gameRatingsRequest));
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
                GameRatingsRequest gameRatingsRequest;
                try {
                    gameRatingsRequest = xmlMapper.readValue(raw, GameRatingsRequest.class);
                } catch (IOException e) {
                    return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+e+"</response></gameRatings>").build();
                }
                try {
                gameRatingsService.update(id, gameRatingsRequest);
                } catch (Exception e) {
                    return Response.status(404, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+e+"</response></gameRatings>").build();
                }
                return Response.status(200, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>Successfully updated</response></gameRatings>").build();
            } else {
                return Response.status(400, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><gameRatings><response>"+validated+"</response></gameRatings>").build();
            }
        } else {
            return Response.status(400, "Please use JSON or XML").build();
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public Response delete(@PathParam("id") final Integer id) {
        try {
            gameRatingsService.delete(id);
            return Response.status(200, "Successfully deleted").build();
        } catch (Exception e) {
            // Executes if the id is not found
            return Response.status(204, e.toString()).build();
        }
    }

    public String validateJson(String gameSalesRequest) {
        try {
            // Validates the Json body of a request and returns true if successful
            InputStream is = getClass().getResourceAsStream("/data/JsonSchemas/gameRatings.json");
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
            // Validates the XML body of a request and returns true if successful
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            javax.xml.validation.Schema schema = schemaFactory.newSchema(new StreamSource(this.getClass().getResourceAsStream("/data/XmlSchemas/gameRatings.xsd")));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(gameSalesRequest.getBytes())));
            return "true";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
