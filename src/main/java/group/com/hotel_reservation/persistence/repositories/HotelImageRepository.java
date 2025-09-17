package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.entities.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {
}
