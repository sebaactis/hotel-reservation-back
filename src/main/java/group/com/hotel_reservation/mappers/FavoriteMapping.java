package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.favorite.FavoriteDto;
import group.com.hotel_reservation.models.entities.Favorite;

public class FavoriteMapping {
    public static FavoriteDto favoriteToDto(Favorite favorite) {
        FavoriteDto favoriteDto = new FavoriteDto();

        favoriteDto.setId(favorite.getId());
        favoriteDto.setUserId(favorite.getUser().getId());
        favoriteDto.setHotelId(favorite.getHotel().getId());
        favoriteDto.setCreatedAt(favorite.getCreatedAt());

        return favoriteDto;
    }
}
