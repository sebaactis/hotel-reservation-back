package group.com.hotel_reservation.persistence.repositories.hotel;

import group.com.hotel_reservation.models.entities.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByName(String name);

    Optional<Hotel> findByName(String name);

    @Query(value = "SELECT * FROM hotel ORDER BY RANDOM() LIMIT 10", nativeQuery = true)
    List<Hotel> getRandomsHotels();

    @Query(value = """
            SELECT * FROM hotel
            ORDER BY md5(CONCAT(:seed, hotel.id))
            OFFSET :offset LIMIT :limit
            """, nativeQuery = true)
    List<Hotel> findAllRandom(@Param("seed") String seed, @Param("offset") int offset, @Param("limit") int limit);

    @Query("""
    SELECT h FROM Hotel h
    WHERE LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))
    AND NOT EXISTS (
        SELECT 1 FROM HotelBooking hb
        WHERE hb.hotel = h
        AND hb.bookedFrom <= :to
        AND hb.bookedTo >= :from
    )
""")
    Page<Hotel> search(@Param("location") String location,
                       @Param("from") LocalDate from,
                       @Param("to") LocalDate to,
                       Pageable pageable);
}