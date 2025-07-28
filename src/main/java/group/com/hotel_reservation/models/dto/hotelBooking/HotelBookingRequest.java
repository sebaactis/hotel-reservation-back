package group.com.hotel_reservation.models.dto.hotelBooking;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelBookingRequest {
    private Long hotelId;
    private Long userId;
    private LocalDate bookedFrom;
    private LocalDate bookedTo;
}
