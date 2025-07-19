package group.com.hotel_reservation.models.dto.hotelBooking;

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
    private Long hotel_id;

    @NotNull
    private LocalDate bookedFrom;

    @NotNull
    private LocalDate bookedTo;
}
