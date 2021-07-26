package henz.sebastian.opendataleipzig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreetRepository extends JpaRepository<Street, Integer> {

    Optional<Street> findByStammdaten_Name(String name);
    Optional<Street> findByStammdaten_Schluessel(String schluessel);
}
