package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.HotelMapper;
import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(HotelRepository hotelRepository, HotelMapper hotelMapper) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    public Hotel create(HotelDto hotelDto) {
        if (hotelRepository.existsByName(hotelDto.getName())) {
            throw new IllegalArgumentException("Ya existe un hotel con el nombre informado");
        }

        Hotel hotel = hotelMapper.toEntity(hotelDto);
        return hotelRepository.save(hotel);
    };
}
