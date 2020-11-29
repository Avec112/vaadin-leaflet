package io.avec.map.data.group;

import io.avec.map.data.place.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
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

    private String groupName;
    private String description;
    private LocationGroupType locationGroupType;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "locationGroup",
            cascade = CascadeType.ALL
    )
    private List<Location> locations = new ArrayList<>();

    public LocationGroup(String groupName, String description, LocationGroupType locationGroupType) {
        this.groupName = groupName;
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
                .append(groupName, locationGroup.groupName)
                .append(description, locationGroup.description)
                .append(locationGroupType, locationGroup.locationGroupType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(groupName)
                .append(description)
                .append(locationGroupType)
                .toHashCode();
    }
}
