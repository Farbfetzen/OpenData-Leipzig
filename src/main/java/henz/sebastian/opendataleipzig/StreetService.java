package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class StreetService {

    @Autowired
    private StreetRepository streetRepository;

    private static class InvalidNumberToNullDeserializer extends StdDeserializer<Integer> {

        InvalidNumberToNullDeserializer(final Class<?> vc) {
            super(vc);
        }

        @Override
        public Integer deserialize(final JsonParser parser,
                                   final DeserializationContext context)
                throws IOException, JsonProcessingException {
            try {
                return Integer.parseInt(parser.getText());
            } catch(final NumberFormatException e) {
                return null;
            }
        }
    }

    private static class EmptyStringToNullDeserializer extends StdDeserializer<String> {

        EmptyStringToNullDeserializer(final Class<?> vc) {
            super(vc);
        }

        @Override
        public String deserialize(final JsonParser parser,
                                  final DeserializationContext context)
                throws IOException, JsonProcessingException {
            final String str = parser.getText();
            return str.isEmpty() ? null : str;
        }
    }

    /** Update the database by deleting all entries and populating it from xml file.
     *
     * @return The number of created records or 0 if it failed.
     */
    long updateStreetData() {
        streetRepository.deleteAllInBatch();

        final SimpleModule customDeserializers = new SimpleModule("MyCustomDeserializers");
        customDeserializers.addDeserializer(Integer.class, new InvalidNumberToNullDeserializer(Integer.class));
        customDeserializers.addDeserializer(String.class, new EmptyStringToNullDeserializer(String.class));
        final ObjectMapper mapper = new XmlMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(customDeserializers);

        final File file = new File("data/Strassenverzeichnis.xml");
        final List<Strasse> streets;
        try {
            streets = Arrays.asList(mapper.readValue(file, Strasse[].class));
        } catch(final IOException e) {
            e.printStackTrace();
            return 0L;
        }
        streetRepository.saveAll(streets);

        // DEBUG: Build the xml to check if its structure is as expected.
        // final StrassenVerzeichnis verzeichnis = new StrassenVerzeichnis();
        // final List<Strasse> streets = new ArrayList<>();
        // final Strasse street1 = new Strasse();
        // street1.setStammdaten(new Strasse.Stammdaten("name1", "key01"));
        // final Strasse street2 = new Strasse();
        // street2.setStammdaten(new Strasse.Stammdaten("name2", "key02"));
        // streets.add(street1);
        // streets.add(street2);
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
