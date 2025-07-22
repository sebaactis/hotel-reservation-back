package group.com.hotel_reservation.mappers;


import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDto;
import group.com.hotel_reservation.models.entities.HotelRating;

public class HotelRatingMapping {

    public static HotelRatingDto hotelRatingToDto(HotelRating hotelRating) {
        HotelRatingDto hotelRatingDto = new HotelRatingDto();

        hotelRatingDto.setHotel(HotelMapping.hotelToHotelDto(hotelRating.getHotel()));
        hotelRatingDto.setUser(UserMapping.userToUserDto(hotelRating.getUser()));
        hotelRatingDto.setScore(hotelRating.getScore());
        hotelRatingDto.setComment(hotelRating.getComment());
        hotelRatingDto.setCreatedAt(hotelRating.getCreatedAt());

        return hotelRatingDto;
    }
}
