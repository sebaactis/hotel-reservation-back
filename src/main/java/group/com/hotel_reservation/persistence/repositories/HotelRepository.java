package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
