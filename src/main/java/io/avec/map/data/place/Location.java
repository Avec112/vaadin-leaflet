package io.avec.map.data.place;

import io.avec.map.data.group.LocationGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Equals to Leaflet latlng
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String locationName;

    private String description;

    @NotNull
    private double lon;

    @NotNull
    private double lat;

    @ManyToOne//(fetch = FetchType.LAZY)
    private LocationGroup locationGroup;

    public Location(@NotNull String locationName, String description, @NotNull double lat, @NotNull double lon) {
        this.locationName = locationName;
        this.description = description;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Location location1 = (Location) o;

        return new EqualsBuilder()
                .append(lon, location1.lon)
                .append(lat, location1.lat)
                .append(id, location1.id)
                .append(locationName, location1.locationName)
                .append(description, location1.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(locationName)
                .append(description)
                .append(lon)
                .append(lat)
                .toHashCode();
    }
}