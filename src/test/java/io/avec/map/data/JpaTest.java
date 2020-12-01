package io.avec.map.data;

import io.avec.map.data.group.LocationGroup;
import io.avec.map.data.vacation.Vacation;
import io.avec.map.data.vacation.VacationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@ImportAutoConfiguration(DataGenerator.class)
class JpaTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private VacationRepository vacationRepository;

    @Test
    void verifyTestConfiguration() {
        assertThat(dataSource).isNotNull();
        assertThat(vacationRepository).isNotNull();
    }

    @Test
    void lazyTest() {
        final List<Vacation> vacations = vacationRepository.findAll();
        assertEquals(1, vacations.size());

        for(Vacation vacation : vacations) {
            final List<LocationGroup> locationGroups = vacation.getLocationGroups();
            assertEquals(3, locationGroups.size()); // hotel, restaurant, museum
            for (LocationGroup locationGroup : locationGroups) {
                switch (locationGroup.getLocationGroupType()) {
                    case HOTEL -> assertEquals(1, locationGroup.getLocations().size());
                    case MUSEUM -> assertEquals(2, locationGroup.getLocations().size());
                    case RESTAURANT -> assertEquals(3, locationGroup.getLocations().size());
                    default -> fail("LocationGroupType." + locationGroup.getLocationGroupType().name().toUpperCase() + " is not handled.");
                }
            }
        }
    }

}