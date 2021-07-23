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

import java.io.IOException;
import java.net.URL;
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

        final List<Street> streets;
        try {
            final URL url = new URL("https://leipzig.de/fachanwendungen/geoinformation/Strassenbeschreibung/Strassenverzeichnis.xml");
            streets = Arrays.asList(mapper.readValue(url, Street[].class));
        } catch(final IOException e) {
            e.printStackTrace();
            return 0L;
        }
        streetRepository.saveAll(streets);

        // DEBUG: Build the xml to check if its structure is as expected.
        // final Street[] streets = new Street[2];
        // final Street street1 = new Street();
        // street1.setStammdaten(new Street.Stammdaten("name1", "key01"));
        // final Street street2 = new Street();
        // street2.setStammdaten(new Street.Stammdaten("name2", "key02"));
        // streets[0] = street1;
        // streets[1] = street2;
        // try {
        //     final String xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(streets);
        //     System.out.println(xml);
        // } catch(final JsonProcessingException e) {
        //     e.printStackTrace();
        // }

        return streetRepository.count();
    }
}
