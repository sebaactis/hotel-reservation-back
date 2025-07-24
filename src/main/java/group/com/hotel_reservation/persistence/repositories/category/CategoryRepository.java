package group.com.hotel_reservation.persistence.repositories.category;

import group.com.hotel_reservation.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByDescription(String description);
}
