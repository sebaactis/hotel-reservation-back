package group.com.hotel_reservation.persistence.repositories.feature;

import group.com.hotel_reservation.models.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    @Query("select f from Feature f where lower(f.name) in :names")
    Set<Feature> findByNameInIgnoreCase(@Param("names") Set<String> names);

    boolean existsByNameIgnoreCase(String name);
}
