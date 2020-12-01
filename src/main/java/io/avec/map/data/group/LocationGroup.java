package io.avec.map.data.group;

import io.avec.map.data.place.Location;
import io.avec.map.data.vacation.Vacation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Equals to Leaflet LayerGroup.
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
public class LocationGroup {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private LocationGroupType locationGroupType;

    @ManyToOne//(fetch = FetchType.LAZY) // EAGER is default
    private Vacation vacation;

    @OneToMany(
            fetch = FetchType.EAGER, // LAZY is default
            mappedBy = "locationGroup",
            cascade = CascadeType.ALL
    )
    private List<Location> locations = new ArrayList<>();

    public LocationGroup(@NotNull String name, @NotNull String description, @NotNull LocationGroupType locationGroupType) {
        this.name = name;
        this.description = description;
        this.locationGroupType = locationGroupType;
    }

    public void addPlace(Location location) {
        locations.add(location);
        location.setLocationGroup(this);
    }

    public void removePlace(Location location) {
        locations.remove(location);
        location.setLocationGroup(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LocationGroup locationGroup = (LocationGroup) o;

        return new EqualsBuilder()
                .append(id, locationGroup.id)
                .append(name, locationGroup.name)
                .append(description, locationGroup.description)
                .append(locationGroupType, locationGroup.locationGroupType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(description)
                .append(locationGroupType)
                .toHashCode();
    }

}
