package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.FavoriteMapping;
import group.com.hotel_reservation.models.dto.favorite.FavoriteDto;
import group.com.hotel_reservation.models.entities.Favorite;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public Boolean existsByUserAndHotel(User user, Hotel hotel) {
        return favoriteRepository.existsByUserAndHotel(user, hotel);
    }

    public List<FavoriteDto> favoritesByUser(User user) {
        return favoriteRepository.findByUser(user).stream().map(FavoriteMapping::favoriteToDto).toList();
    }

    public FavoriteDto create(User user, Hotel hotel) throws Exception {

        if(existsByUserAndHotel(user, hotel)) {
            throw new Exception("Ya existe un favorito con estos datos");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setHotel(hotel);
        favorite.setCreatedAt(new Date());

        return FavoriteMapping.favoriteToDto(favoriteRepository.save(favorite));
    }

    public FavoriteDto findOneByUserAndHotel(User user, Hotel hotel) {
        return FavoriteMapping.favoriteToDto(favoriteRepository.findByUserAndHotel(user, hotel));
    }

    public Boolean delete(User user, Hotel hotel) {
        Favorite favoriteToDelete = favoriteRepository.findByUserAndHotel(user, hotel);

        if(favoriteToDelete.getId() == null) {
            return false;
        }

        favoriteRepository.delete(favoriteToDelete);
        return true;
    }
}
