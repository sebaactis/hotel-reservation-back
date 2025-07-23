package group.com.hotel_reservation.models.dto.hotelRating;

import group.com.hotel_reservation.models.dto.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelRatingDetailsDto {

    private Float score;
    private Long hotel_id;
    private UserDto user;
    private String comment;
    private LocalDateTime date;
}
