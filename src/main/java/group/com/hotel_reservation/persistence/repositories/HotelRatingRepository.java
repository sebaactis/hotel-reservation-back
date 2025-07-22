package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.entities.HotelRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRatingRepository extends JpaRepository<HotelRating, Long> {

    List<HotelRating> findByHotelId(Long hotelId);

    @Query("SELECT AVG(r.score) FROM HotelRating r WHERE r.hotel.id = :hotelId")
    Double findAverageScoreByHotelId(@Param("hotelId") Long hotelId);
}
