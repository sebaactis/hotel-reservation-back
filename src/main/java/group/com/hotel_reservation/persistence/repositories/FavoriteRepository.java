package group.com.hotel_reservation.persistence.repositories;

import group.com.hotel_reservation.models.entities.Favorite;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Boolean existsByUserAndHotel(User user, Hotel hotel);

    List<Favorite> findByUser(User user);

    Favorite findByUserAndHotel(User user, Hotel hotel);
}
