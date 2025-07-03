package group.com.hotel_reservation.services;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel create(HotelDto hotelDto) {
        if (hotelRepository.existsByName(hotelDto.getName())) {
            throw new IllegalArgumentException("Ya existe un hotel con el nombre informado");
        }
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setFeatures(hotelDto.getFeatures());
        hotel.setPrice(hotelDto.getPrice());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setScore(hotelDto.getScore());
        hotel.setDiscount(hotelDto.getDiscount());
        return hotelRepository.save(hotel);
    };
}
