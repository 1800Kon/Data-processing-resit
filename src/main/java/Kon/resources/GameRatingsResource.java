package Kon.resources;

import Kon.models.gameRatings.client.GameRatings;
import Kon.models.gameRatings.client.GameRatingsRequest;
import Kon.services.GameRatingsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.xml.XmlMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Collection<GameRatings> getAll() {
        return gameRatingsService.getAll();
    }

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String save(final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                try {
                    GameRatingsRequest gameRatingsRequest = objectMapper.readValue(raw, GameRatingsRequest.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    ow.writeValueAsString(gameRatingsService.save(gameRatingsRequest));
                    return "Successfully added.";
                } catch (JsonProcessingException e) {
                    return e.toString();
                }
            } else {
                return validated;
            }
        } else if (request.getHeader("Accept").equals("application/xml") && request.getHeader("Content-Type").equals("application/xml")) {
            String validated = validateXml(raw);
            if (validated.equals("true")) {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    GameRatingsRequest gameRatingsRequest = xmlMapper.readValue(raw, GameRatingsRequest.class);
                    gameRatingsService.save(gameRatingsRequest);
                    return "Successfully added.";
                } catch (IOException e) {
                    return e.toString();
                }
            } else {
                return validated;
            }
        } else {
            return "Please use JSON or XML.";
        }
    }

    @PUT
    @Path("/put/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String update(@PathParam("id") final Integer id, final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                GameRatingsRequest gameRatingsRequest;
                try {
                    gameRatingsRequest = objectMapper.readValue(raw, GameRatingsRequest.class);
                } catch (JsonProcessingException e) {
                    return e.toString();
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    ow.writeValueAsString(gameRatingsService.update(id, gameRatingsRequest));
                } catch (Exception e) {
                    return e.toString();
                }
                return "Successfully updated.";
            } else {
                return validated;
            }
        } else if (request.getHeader("Accept").equals("application/xml") && request.getHeader("Content-Type").equals("application/xml")) {
            String validated = validateXml(raw);
            if (validated.equals("true")) {
                XmlMapper xmlMapper = new XmlMapper();
                GameRatingsRequest gameRatingsRequest;
                try {
                    gameRatingsRequest = xmlMapper.readValue(raw, GameRatingsRequest.class);
                } catch (IOException e) {
                    return e.toString();
                }
                try {
                gameRatingsService.update(id, gameRatingsRequest);
                } catch (Exception e) {
                    return e.toString();
                }
                return "Successfully updated.";
            } else {
                return validated;
            }
        } else {
            return "Please use JSON or XML.";
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public String delete(@PathParam("id") final Integer id) {
        try {
            gameRatingsService.delete(id);
            return "Successfully deleted.";
        } catch (Exception e) {
            return e.toString();
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
