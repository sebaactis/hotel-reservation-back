package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingDto;
import group.com.hotel_reservation.models.entities.HotelBooking;

public class HotelBookingMapping {
    public static HotelBookingDto hotelBookingToDto(HotelBooking hotelBooking) {
        HotelBookingDto dto = new HotelBookingDto();

        dto.setId(hotelBooking.getId());
        dto.setHotel_id(hotelBooking.getHotel().getId());
        dto.setBookedFrom(hotelBooking.getBookedFrom());
        dto.setBookedTo(hotelBooking.getBookedTo());

        return dto;
    }
}
