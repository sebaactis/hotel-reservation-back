package group.com.hotel_reservation.models.dto.hotelBooking;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelBookingRequest {
    @NotNull
    private Long hotelId;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDate bookedFrom;

    @NotNull
    private LocalDate bookedTo;

    @NotNull
    private Integer guests;

    @NotNull
    private Integer nights;

    @NotNull
    private Double totalPrice;
}
