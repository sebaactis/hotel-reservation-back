package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByDescription(String description);
}
