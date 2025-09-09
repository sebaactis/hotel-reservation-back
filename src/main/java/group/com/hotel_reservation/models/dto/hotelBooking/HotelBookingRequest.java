package group.com.hotel_reservation.models.dto.hotelBooking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    @Min(1)
    private Integer guests;

    @NotNull
    @Min(1)
    private Integer nights;

    @NotNull
    @Min(1)
    private Double totalPrice;
}
