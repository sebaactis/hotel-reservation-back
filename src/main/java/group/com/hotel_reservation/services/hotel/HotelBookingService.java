package group.com.hotel_reservation.services.hotel;

import group.com.hotel_reservation.mappers.HotelBookingMapping;
import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingDto;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelBookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelBookingService {
    private final HotelBookingRepository repository;

    public HotelBookingService(HotelBookingRepository repository) {
        this.repository = repository;
    }

    public List<HotelBookingDto> findByHotelId(Long id) {
        return repository.findByHotelId(id).stream().map(HotelBookingMapping::hotelBookingToDto).toList();
    }
}
