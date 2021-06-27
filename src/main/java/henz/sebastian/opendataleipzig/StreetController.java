package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @PostMapping(path = "/update")
    @ResponseStatus(HttpStatus.OK)
    public void update() {

        // Populate the database from an xml file. This is done
        // for development purposes. Later it will download the data
        // from the web.
        // The data is parsed sequentially. This is inefficient and
        // should be done with a batch method.
        // TODO: Learn how to do batch xml parsing.

        final File file = new File("data/Strassenverzeichnis.xml");
        try {
            final XmlMapper xmlMapper = new XmlMapper();
            final JsonNode streetArrayNode = xmlMapper.readTree(file).get("STRASSE");
            for (final JsonNode streetNode : streetArrayNode) {
                final Street street = new Street();
                final JsonNode coreDataNode = streetNode.get("STAMMDATEN");
                street.setName(coreDataNode.get("NAME").asText());
                street.setKey(coreDataNode.get("SCHLUESSEL").asText());
                streetRepository.save(street);
            }
        } catch(final IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/getall")
    @ResponseBody
    public List<Street> getAllStreets() {
        return streetRepository.findAll();
    }

    @PostMapping(path = "/add")
    @ResponseBody
    public Street addStreet(@RequestParam final String name, @RequestParam final String key) {
        return streetRepository.save(new Street(name, key));
    }

}
