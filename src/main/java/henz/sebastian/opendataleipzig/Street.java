package henz.sebastian.opendataleipzig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Street {

    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String name;
    @Id
    @Column(unique = true, length = 5)
    private String key;

    // Use Integer instead of int because these could be null.
    private Integer length;
    private Integer population;
}
