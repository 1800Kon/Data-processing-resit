package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.services.ConsoleSalesService;
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

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ConsoleSalesService consoleSalesService;

    @Autowired
    HttpServletRequest request;

    @POST
    @Path("/post")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String save(final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                try {
                    ConsoleSalesRequest consoleSalesRequest = objectMapper.readValue(raw, ConsoleSalesRequest.class);
                    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    ow.writeValueAsString(consoleSalesService.save(consoleSalesRequest));
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
                    ConsoleSalesRequest consoleSalesRequest = xmlMapper.readValue(raw, ConsoleSalesRequest.class);
                    consoleSalesService.save(consoleSalesRequest);
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


    @GET
    @Path("/getall")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<ConsoleSales> getAll() {
        return consoleSalesService.getAll();
    }

    @PUT
    @Path("/put/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public String update(@PathParam("id") final Integer id, final String raw) {
        if (request.getHeader("Accept").equals("application/json") && request.getHeader("Content-Type").equals("application/json")) {
            String validated = validateJson(raw);
            if (validated.equals("true")) {
                ConsoleSalesRequest consoleSalesRequest;
                try {
                    consoleSalesRequest = objectMapper.readValue(raw, ConsoleSalesRequest.class);
                } catch (Exception e) {
                    return e.toString();
                }
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                try {
                    ow.writeValueAsString(consoleSalesService.update(id, consoleSalesRequest));
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
                ConsoleSalesRequest consoleSalesRequest;
                try {
                    consoleSalesRequest = xmlMapper.readValue(raw, ConsoleSalesRequest.class);
                } catch (Exception e) {
                    return e.toString();
                }
                try {
                consoleSalesService.update(id, consoleSalesRequest);
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
            consoleSalesService.delete(id);
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
