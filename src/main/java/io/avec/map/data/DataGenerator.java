package io.avec.map.data;

import io.avec.map.data.group.LocationGroup;
import io.avec.map.data.group.LocationGroupRepository;
import io.avec.map.data.group.LocationGroupType;
import io.avec.map.data.place.Location;
import io.avec.map.data.vacation.Vacation;
import io.avec.map.data.vacation.VacationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class DataGenerator {


    @Bean
    public CommandLineRunner createData(VacationRepository repository) {
        return args -> {

            final List<Vacation> vacations = Collections.singletonList(
                    new Vacation("Norway", "Short but nice trip to Oslo, 2019.") // Norway, Oslo
            );

            // Add Location groups
            vacations.get(0).addLocationGroup(new LocationGroup("Hotels", "Where I lived.", LocationGroupType.HOTEL));
            vacations.get(0).addLocationGroup(new LocationGroup("Museums", "Cultural visits.", LocationGroupType.MUSEUM));
            vacations.get(0).addLocationGroup(new LocationGroup("Restaurants", "Mmmm, very tasty.", LocationGroupType.RESTAURANT));

            // Hotels
            vacations.get(0).getLocationGroups().get(0).addPlace(new Location("Comfort Hotel Karl Johan", "3-star Hotel", LocalDate.of(2019,1,1), 59.91196666927608, 10.746121956821368));

            // Museums
            vacations.get(0).getLocationGroups().get(1).addPlace(new Location("Astrup Fearnley", "Museum of Modern Art", LocalDate.of(2019,1,1), 59.90724956454675, 10.721575319314997));
            vacations.get(0).getLocationGroups().get(1).addPlace(new Location("Historical Museum", "Collection of international antiquities", LocalDate.of(2019,1,2), 59.91691529230013, 10.735482389023014));

            // Restaurants
            vacations.get(0).getLocationGroups().get(2).addPlace(new Location("Jaipur Restaurant", "Jaipur Indisk Restaurant", LocalDate.of(2019,1,1), 59.912786996692724, 10.741708582461488));
            vacations.get(0).getLocationGroups().get(2).addPlace(new Location("Habibi restaurant og kafé", "", LocalDate.of(2019,1,2), 59.913583013270376, 10.749261682853477));
            vacations.get(0).getLocationGroups().get(2).addPlace(new Location("Grand Café Oslo", "", LocalDate.of(2019,1,3), 59.91394336585766, 10.73900491562362));

            // Save all
            repository.saveAll(vacations);
        };
    }
}
