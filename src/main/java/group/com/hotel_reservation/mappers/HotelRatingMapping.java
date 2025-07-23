package group.com.hotel_reservation.mappers;


import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDetailsDto;
import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDto;
import group.com.hotel_reservation.models.dto.user.UserDto;
import group.com.hotel_reservation.models.entities.HotelRating;
import group.com.hotel_reservation.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelRatingMapping {

    private final UserService userService;

    @Autowired
    public HotelRatingMapping(UserService userService) {
        this.userService = userService;
    }

    public HotelRatingDto hotelRatingToDto(HotelRating hotelRating) {
        HotelRatingDto hotelRatingDto = new HotelRatingDto();

        hotelRatingDto.setHotel(HotelMapping.hotelToHotelDto(hotelRating.getHotel()));
        hotelRatingDto.setUser(UserMapping.userToUserDto(hotelRating.getUser()));
        hotelRatingDto.setScore(hotelRating.getScore());
        hotelRatingDto.setComment(hotelRating.getComment());
        hotelRatingDto.setCreatedAt(hotelRating.getCreatedAt());

        return hotelRatingDto;
    }

    public HotelRatingDetailsDto hotelRatingToDetailsDto(HotelRating hotelRating) {
        HotelRatingDetailsDto dto = new HotelRatingDetailsDto();

        dto.setScore(hotelRating.getScore());
        dto.setDate(hotelRating.getCreatedAt());
        dto.setHotel_id(hotelRating.getHotel().getId());
        dto.setComment(hotelRating.getComment());

        // Mapear el usuario
        UserDto userDto = userService.getUserDto(hotelRating.getUser().getId());
        dto.setUser(userDto);

        return dto;
    }
}
