package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/streets")
public class StreetController {

    @Autowired
    private StreetRepository streetRepository;

    @PostMapping(path = "/update")
    public ResponseEntity<String> update() {
        final long numberOfRecords = updateStreetData();
        if (numberOfRecords > 0) {
            return new ResponseEntity<>(
                "Updated database with " + numberOfRecords + " records.",
                HttpStatus.CREATED
            );
        }
        return new ResponseEntity<>(
            "Failed to update database.",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Strasse> getAllStreets() {
        return streetRepository.findAll();
    }

    // @PostMapping(path = "/add")
    // public Street addStreet(@RequestBody @Valid final Street street) {
    //     return streetRepository.save(street);
    // }

    /** Update the database by deleting all entries and populating it from xml file.
     *
     * @return The number of created records or 0 if it failed.
     */
    private long updateStreetData() {
        streetRepository.deleteAllInBatch();

        final SimpleModule customDeserializers = new SimpleModule("MyCustomDeserializers");
        customDeserializers.addDeserializer(Integer.class, new InvalidNumberToNullDeserializer(Integer.class));
        customDeserializers.addDeserializer(String.class, new EmptyStringToNullDeserializer(String.class));
        final ObjectMapper mapper = new XmlMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(customDeserializers);

        final File file = new File("data/Strassenverzeichnis.xml");
        final StrassenVerzeichnis sv;
        try {
            sv = mapper.readValue(file, StrassenVerzeichnis.class);
        } catch(final IOException e) {
            e.printStackTrace();
            return 0L;
        }
        streetRepository.saveAll(sv.getStrassen());

        // DEBUG: Baue das xml auf und checke so, ob die Struktur dem gewünschten entspricht.
        // final StrassenVerzeichnis verzeichnis = new StrassenVerzeichnis();
        // final List<Strasse> streets = new ArrayList<>();
        // streets.add(new Strasse(1, "foo", "bar"));
        // streets.add(new Strasse(2, "hurr", "baz"));
        // verzeichnis.setStrassen(streets);
        // try {
        //     final String xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(verzeichnis);
        //     System.out.println(xml);
        // } catch(final JsonProcessingException e) {
        //     System.out.println("Error processing sv object.");
        //     e.printStackTrace();
        // }

        return streetRepository.count();
    }
}


class InvalidNumberToNullDeserializer extends StdDeserializer<Integer> {

    protected InvalidNumberToNullDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public Integer deserialize(final JsonParser parser,
                               final DeserializationContext context)
            throws IOException {
        try {
            return Integer.parseInt(parser.getText());
        } catch(final NumberFormatException e) {
            return null;
        }
    }
}


class EmptyStringToNullDeserializer extends StdDeserializer<String> {

    protected EmptyStringToNullDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(final JsonParser parser,
                              final DeserializationContext context)
            throws IOException {
        final String str = parser.getText();
        return str.isEmpty() ? null : str;
    }
}
