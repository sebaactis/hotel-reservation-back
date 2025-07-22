package group.com.hotel_reservation.models.dto.hotelRating;

import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.dto.user.UserDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRatingDto {

    private HotelDto hotel;
    private UserDto user;
    private Float score;
    private String comment;
    private LocalDateTime createdAt = LocalDateTime.now();
}
