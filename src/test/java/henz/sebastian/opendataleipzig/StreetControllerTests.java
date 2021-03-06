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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

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
        final String responseString = Objects.requireNonNull(response.getBody());
        final int n = Integer.parseInt(responseString.replaceAll("\\D+", ""));
        // The number of streets may change. That's why it is checked to be greater than 3000.
        Assertions.assertTrue(n > 3000, "Number of Streets should be greater than 3000 but is " + n + ".");
    }

    @Test
    void getAllReturnsCorrectNumberOfStreets() {
        final Street[] streets = testRestTemplate.getForObject(url + "/all", Street[].class);
        // The number of streets may change. That's why it is checked to be greater than 3000.
        Assertions.assertTrue(
            streets.length > 3000,
            "Number of Streets should be greater than 3000 but is " + streets.length + "."
        );
    }

    @Test
    void sortByLengthDefault() {
        final StreetPageContent streetPage = testRestTemplate.getForObject(
            url + "/sortby?param=Charakteristika.laenge",
            StreetPageContent.class
        );
        final Street[] streets = streetPage.getStreets();
        Assertions.assertAll(
            () -> Assertions.assertEquals("Prager Stra??e", streets[0].getStammdaten().getName()),
            () -> Assertions.assertEquals("Albersdorfer Stra??e", streets[9].getStammdaten().getName())
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
            () -> Assertions.assertEquals("Gerhard-Ellrodt-Stra??e", streets[0].getStammdaten().getName()),
            () -> Assertions.assertEquals("Richard-Lehmann-Stra??e", streets[9].getStammdaten().getName())
        );
    }

    @Test
    void GetByName() {
        final String nameUrl = url + "/get?name={name}";
        final Street street = testRestTemplate.getForObject(
            nameUrl,
            Street.class,
            "Georg-Schumann-Stra??e"
        );
        final ResponseEntity<String> notFoundResponse = testRestTemplate.getForEntity(
            nameUrl,
            String.class,
            "Foo"
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals("07001", street.getStammdaten().getSchluessel()),
            () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode())
        );
    }

    @Test
    void GetBySchluessel() {
        final String schluesselUrl = url + "/get?schluessel={schluessel}";
        final Street street = testRestTemplate.getForObject(
            schluesselUrl,
            Street.class,
            "01016"
        );
        final ResponseEntity<String> notFoundResponse = testRestTemplate.getForEntity(
            schluesselUrl,
            String.class,
            "12345"
        );
        final ResponseEntity<String> badRequestResponse = testRestTemplate.getForEntity(
            schluesselUrl,
            String.class,
            "abcde"
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals("Markt", street.getStammdaten().getName()),
            () -> Assertions.assertEquals(HttpStatus.NOT_FOUND, notFoundResponse.getStatusCode()),
            () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, badRequestResponse.getStatusCode())
        );
    }
}
