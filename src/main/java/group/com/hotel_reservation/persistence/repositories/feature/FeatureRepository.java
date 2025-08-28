package group.com.hotel_reservation.persistence.repositories.feature;

import group.com.hotel_reservation.models.entities.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    boolean existsByNameIgnoreCase(String name);
}
