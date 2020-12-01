package io.avec.map.data.vacation;

import io.avec.map.data.group.LocationGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Vacation {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "vacation",
            cascade = CascadeType.ALL
    )
    private List<LocationGroup> locationGroups = new ArrayList<>();

    public Vacation(@NotNull String name, @NotNull String description) {
        this.name = name;
        this.description = description;
    }

    public void addLocationGroup(LocationGroup locationGroup) {
        locationGroups.add(locationGroup);
        locationGroup.setVacation(this);
    }

    public void removeLocationGroup(LocationGroup locationGroup) {
        locationGroups.remove(locationGroup);
        locationGroup.setVacation(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vacation vacation = (Vacation) o;

        return new EqualsBuilder()
                .append(id, vacation.id)
                .append(name, vacation.name)
                .append(description, vacation.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(description)
                .toHashCode();
    }
}
