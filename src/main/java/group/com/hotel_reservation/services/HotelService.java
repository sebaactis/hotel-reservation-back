package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.HotelMapping;
import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.dto.HotelImageDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelImage;
import group.com.hotel_reservation.persistence.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Hotel hotel = HotelMapping.hotelDtoToHotel(hotelDto);
        return hotelRepository.save(hotel);
    };

    public Boolean delete(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID" + id));

         hotelRepository.delete(hotel);

         return true;

    };

    public Page<HotelDto> getAll(Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);

        return hotels.map(HotelMapping::hotelToHotelDto);
    }

    public HotelDto getOne(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID"));

        return HotelMapping.hotelToHotelDto(hotel);
    }

    public List<HotelDto> getRandomHotels() {
        return hotelRepository.getRandomsHotels().stream()
                .map(HotelMapping::hotelToHotelDto)
                .collect(Collectors.toList());
    }
}
