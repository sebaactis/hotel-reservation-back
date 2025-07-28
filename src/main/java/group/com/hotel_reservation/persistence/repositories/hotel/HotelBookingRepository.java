package group.com.hotel_reservation.persistence.repositories.hotel;

import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Long> {
    @Query("SELECT h FROM HotelBooking h WHERE h.hotel.id = :hotelId")
    List<HotelBooking> findByHotelId(@Param("hotelId") Long hotelId);

    @Query("SELECT h FROM HotelBooking h WHERE h.user.id = :userId")
    List<HotelBooking> findByUserId(@Param("userId") Long userId);
}
