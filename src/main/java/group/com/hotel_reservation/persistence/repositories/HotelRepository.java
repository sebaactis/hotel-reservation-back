package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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


}
