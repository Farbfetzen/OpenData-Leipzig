package henz.sebastian.opendataleipzig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StreetControllerTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private final String url;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class StreetPageContent {

        @JsonProperty("content")
        private Street[] streets;
    }

    StreetControllerTests(@Value("${server.port}") final int port) {
        url = "http://localhost:" + port + "/streets";
    }

    @Test
    void updateReturnsCorrectStatusAndBody() {
        final ResponseEntity<String> response = testRestTemplate.postForEntity(
            url + "/update",
            null,
            String.class
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(201, response.getStatusCodeValue()),
            () -> Assertions.assertEquals("Updated database with 3054 records.", response.getBody())
        );
    }

    @Test
    void getAllReturnsCorrectNumberOfStreets() {
        final Street[] streets = testRestTemplate.getForObject(url + "/all", Street[].class);
        Assertions.assertEquals(3054, streets.length);
    }

    @Test
    void sortByLengthDefault() {
        final StreetPageContent streetPage = testRestTemplate.getForObject(
            url + "/sortby?param=Charakteristika.laenge",
            StreetPageContent.class
        );
        final Street[] streets = streetPage.getStreets();
        Assertions.assertAll(
            () -> Assertions.assertEquals("Prager Straße", streets[0].getStammdaten().getName()),
            () -> Assertions.assertEquals("Albersdorfer Straße", streets[9].getStammdaten().getName())
        );
    }

    @Test
    void sortByLengthDescPage1() {
        final StreetPageContent response = testRestTemplate.getForObject(
            url + "/sortby?param=Charakteristika.laenge&page=1",
            StreetPageContent.class
        );
        final Street[] streets = response.getStreets();
        Assertions.assertAll(
            () -> Assertions.assertEquals("Gerhard-Ellrodt-Straße", streets[0].getStammdaten().getName()),
            () -> Assertions.assertEquals("Richard-Lehmann-Straße", streets[9].getStammdaten().getName())
        );
    }
}
