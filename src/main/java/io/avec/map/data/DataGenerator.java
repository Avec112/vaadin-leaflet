package io.avec.map.data;

import io.avec.map.data.group.LocationGroup;
import io.avec.map.data.group.LocationGroupRepository;
import io.avec.map.data.group.LocationGroupType;
import io.avec.map.data.place.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataGenerator {


    @Bean
    public CommandLineRunner createData(LocationGroupRepository groupRepository) {
        return args -> {

            // Create some groups
            final List<LocationGroup> locationGroups = Arrays.asList(
                    new LocationGroup("Hotels", "Where I lived.", LocationGroupType.HOTEL),
                    new LocationGroup("Museums", "Cultural visits.", LocationGroupType.MUSEUM),
                    new LocationGroup("Restaurants", "Mmmm, very tasty.", LocationGroupType.RESTAURANT)
            );

            // Hotels
            locationGroups.get(0).addPlace(new Location("Comfort Hotel Karl Johan", "3-star Hotel", 59.91196666927608, 10.746121956821368));

            // Museums
            locationGroups.get(1).addPlace(new Location("Astrup Fearnley", "Museum of Modern Art", 59.90724956454675, 10.721575319314997));
            locationGroups.get(1).addPlace(new Location("Historical Museum", "Collection of international antiquities", 59.91691529230013, 10.735482389023014));

            // Restaurants
            locationGroups.get(2).addPlace(new Location("Jaipur Restaurant", "Jaipur Indisk Restaurant", 59.912786996692724, 10.741708582461488));
            locationGroups.get(2).addPlace(new Location("Habibi restaurant og kafé", "", 59.913583013270376, 10.749261682853477));
            locationGroups.get(2).addPlace(new Location("Grand Café Oslo", "", 59.91394336585766, 10.73900491562362));

            // Save all
            groupRepository.saveAll(locationGroups);
        };
    }
}
