package group.com.hotel_reservation.services.hotel;

import group.com.hotel_reservation.mappers.HotelBookingMapping;
import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingDto;
import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingRequest;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelBooking;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelBookingRepository;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelRepository;
import group.com.hotel_reservation.persistence.repositories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HotelBookingService {
    private final HotelBookingRepository hotelBookingRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public HotelBookingService(HotelBookingRepository hotelBookingRepository, HotelRepository hotelRepository, UserRepository userRepository) {
        this.hotelBookingRepository = hotelBookingRepository;
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
    }

    public List<HotelBookingDto> findByHotelId(Long id) {
        return hotelBookingRepository.findByHotelId(id).stream().map(HotelBookingMapping::hotelBookingToDto).toList();
    }

    public List<HotelBookingDto> findByUserId(Long id) {
        return hotelBookingRepository.findByUserId(id).stream().map(HotelBookingMapping::hotelBookingToDto).toList();
    }

    public HotelBookingDto createBooking(HotelBookingRequest request) {
        Optional<Hotel> hotel = hotelRepository.findById(request.getHotelId());
        Optional<User> user = userRepository.findById(request.getUserId());

        if(hotel.isEmpty() || user.isEmpty()) {
            throw new RuntimeException("Hotel o Usuario no encontrado");
        }

        HotelBooking booking = new HotelBooking();
        booking.setHotel(hotel.get());
        booking.setUser(user.get());
        booking.setBookedFrom(request.getBookedFrom());
        booking.setBookedTo(request.getBookedTo());
        booking.setGuests(request.getGuests());
        booking.setNights(request.getNights());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setCreatedAt(LocalDate.now());

        return HotelBookingMapping.hotelBookingToDto(hotelBookingRepository.save(booking));
    }
}
