package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.models.videogameSales.client.VideogameSales;
import Kon.models.videogameSales.client.VideogameSalesRequest;
import Kon.services.ConsoleSalesService;
import Kon.services.VideogameSalesService;
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

@Path("/videogameSales")
@Component
public class VideogameSalesResource {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    VideogameSalesService videogameSalesService;

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String save(final String raw) {
        String trimmed = raw.trim();
        String firstChar = String.valueOf(trimmed.charAt(0));
        if (firstChar.equals("{")) {
            if (validateJson(raw)) {
                try {
                    VideogameSalesRequest videogameSalesRequest = objectMapper.readValue(raw, VideogameSalesRequest.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    ow.writeValueAsString(videogameSalesService.save(videogameSalesRequest));
                    return "Successfully added.";
                } catch (JsonProcessingException e) {
                    return "Invalid format.";
                }
            } else {
                return "Invalid format.";
            }
        } else if (firstChar.equals("<")) {
            if (validateXml(raw)) {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    VideogameSalesRequest videogameSalesRequest = xmlMapper.readValue(raw, VideogameSalesRequest.class);
                    videogameSalesService.save(videogameSalesRequest);
                    return "Successfully added.";
                } catch (IOException e) {
                    return "Invalid format.";
                }
            } else {
                return "Invalid format.";
            }
        } else {
            return "Please use JSON or XML.";
        }
    }

    @GET
    @Path("/getall")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<VideogameSales> getAll() {
        return videogameSalesService.getAll();
    }


    @PUT
    @Path("/put/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String update(@PathParam("id") final Integer id, final String raw) {
        String trimmed = raw.trim();
        String firstChar = String.valueOf(trimmed.charAt(0));
        if (firstChar.equals("{")) {
            if (validateJson(raw)) {
                VideogameSalesRequest videogameSalesRequest;
                try {
                    videogameSalesRequest = objectMapper.readValue(raw, VideogameSalesRequest.class);
                } catch (JsonProcessingException e) {
                    return "Invalid format.";
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    ow.writeValueAsString(videogameSalesService.update(id, videogameSalesRequest));
                } catch (JsonProcessingException e) {
                    return "Invalid format.";
                }
                return "Successfully updated.";
            } else {
                return "Invalid format.";
            }
        } else if (firstChar.equals("<")) {
            if (validateXml(raw)) {
                XmlMapper xmlMapper = new XmlMapper();
                VideogameSalesRequest videogameSalesRequest;
                try {
                    videogameSalesRequest = xmlMapper.readValue(raw, VideogameSalesRequest.class);
                } catch (IOException e) {
                    return "Invalid format.";
                }
                videogameSalesService.update(id, videogameSalesRequest);
                return "Successfully updated.";
            } else {
                return "Invalid format.";
            }
        } else {
            return "Please use JSON or XML.";
        }
    }

    @DELETE
    @Path("/delete/{id}")
    public String delete(@PathParam("id") final Integer id) {
        try {
            videogameSalesService.delete(id);
            return "Successfully deleted.";
        } catch (EmptyResultDataAccessException e) {
            return "No entry with this id was found.";
        }
    }


    public Boolean validateJson(String consoleSalesRequest) {
        try {
            InputStream is = getClass().getResourceAsStream("/data/JsonSchemas/videoGameSalesWithRatings.json");
            JSONObject rawSchema = new JSONObject(new JSONTokener(is));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(consoleSalesRequest));
            return true;
        } catch (ValidationException e) {
            return false;
        }
    }

    public Boolean validateXml(String consoleSalesRequest) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            javax.xml.validation.Schema schema = schemaFactory.newSchema(new StreamSource(this.getClass().getResourceAsStream("/data/XmlSchemas/videoGameSalesWithRatings.xsd")));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(consoleSalesRequest.getBytes())));
            return true;
        } catch (IOException | SAXException e) {
            return false;
        }
    }
}
