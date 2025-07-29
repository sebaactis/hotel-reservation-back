package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingDto;
import group.com.hotel_reservation.models.entities.HotelBooking;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelRepository;
import group.com.hotel_reservation.persistence.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

public class HotelBookingMapping {

    public static HotelBookingDto hotelBookingToDto(HotelBooking hotelBooking) {
        HotelBookingDto dto = new HotelBookingDto();

        dto.setId(hotelBooking.getId());
        dto.setHotel(HotelMapping.hotelToHotelDto(hotelBooking.getHotel()));
        dto.setUser(UserMapping.userToUserDto(hotelBooking.getUser()));
        dto.setBookedFrom(hotelBooking.getBookedFrom());
        dto.setBookedTo(hotelBooking.getBookedTo());
        dto.setGuests(hotelBooking.getGuests());
        dto.setNights(hotelBooking.getNights());
        dto.setCreatedAt(hotelBooking.getCreatedAt());
        dto.setTotalPrice(hotelBooking.getTotalPrice());

        return dto;
    }
}
