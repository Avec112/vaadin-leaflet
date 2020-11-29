package io.avec.map.data.group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long> {

    List<LocationGroup> findLocationGroupByLocationGroupType(LocationGroupType locationGroupType);
}
