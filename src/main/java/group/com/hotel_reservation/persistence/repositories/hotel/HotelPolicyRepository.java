package group.com.hotel_reservation.persistence.repositories.hotel;

import group.com.hotel_reservation.models.entities.HotelPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelPolicyRepository extends JpaRepository<HotelPolicy, Long> {
    List<HotelPolicy> findByHotelId(Long hotelId);
}
