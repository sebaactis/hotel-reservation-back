package group.com.hotel_reservation.models.dto.hotelBooking;

import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.dto.user.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class HotelBookingDto {

    @NotNull
    private Long id;

    @NotNull
    private HotelDto hotel;

    @NotNull
    private UserDto user;

    @NotNull
    private LocalDate bookedFrom;

    @NotNull
    private LocalDate bookedTo;
}
