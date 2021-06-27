package henz.sebastian.opendataleipzig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.UnmarshallerRepositoryPopulatorFactoryBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class JpaPopulators {

    // Populate the database when the application starts.
    // Code taken from https://www.baeldung.com/spring-data-jpa-repository-populators
    @Bean
    public UnmarshallerRepositoryPopulatorFactoryBean repositoryPopulator() {
        final Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(Street.class);

        final UnmarshallerRepositoryPopulatorFactoryBean factory = new UnmarshallerRepositoryPopulatorFactoryBean();
        factory.setUnmarshaller(unmarshaller);

        factory.setResources(new Resource[] { new ClassPathResource("data/Strassenverzeichnis-Auszug-reduziert-einzel.xml") });
        return factory;
    }

}
