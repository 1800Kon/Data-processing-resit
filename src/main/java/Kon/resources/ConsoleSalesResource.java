package Kon.resources;

import Kon.models.consoleSales.client.ConsoleSales;
import Kon.models.consoleSales.client.ConsoleSalesRequest;
import Kon.services.ConsoleSalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.xml.XmlMapper;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

@Path("/consoleSales")
@Component
public class ConsoleSalesResource {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    ConsoleSalesService consoleSalesService;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ConsoleSales save(final String raw) {
        String trimmed = raw.trim();
        String firstChar = String.valueOf(trimmed.charAt(0));
        if (firstChar.equals("{")) {
            if (validateJson(raw)) {
                try {
                    ConsoleSalesRequest consoleSalesRequest = objectMapper.readValue(raw, ConsoleSalesRequest.class);
                    return consoleSalesService.save(consoleSalesRequest);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } else if (firstChar.equals("<")) {
            if (validateXml(raw)) {
                try {
                    XmlMapper xmlMapper = new XmlMapper();
                    ConsoleSalesRequest consoleSalesRequest = xmlMapper.readValue(raw, ConsoleSalesRequest.class);
                    return consoleSalesService.save(consoleSalesRequest);
                } catch (JsonMappingException | JsonParseException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
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

    public Boolean validateXml(String consoleSalesRequest) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            javax.xml.validation.Schema schema = schemaFactory.newSchema(new StreamSource(this.getClass().getResourceAsStream("/data/XmlSchemas/consoleManufacturerSales.xsd")));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new ByteArrayInputStream(consoleSalesRequest.getBytes())));
            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
