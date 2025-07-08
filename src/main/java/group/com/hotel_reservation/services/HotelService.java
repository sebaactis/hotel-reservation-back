package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.HotelMapping;
import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.dto.hotel.HotelUpdateDto;
import group.com.hotel_reservation.models.entities.Category;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.CategoryRepository;
import group.com.hotel_reservation.persistence.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CategoryRepository categoryRepository;

    public HotelService(HotelRepository hotelRepository, CategoryRepository categoryRepository) {
        this.hotelRepository = hotelRepository;
        this.categoryRepository = categoryRepository;
    }

    public Hotel create(HotelDto hotelDto) {
        if (hotelRepository.existsByName(hotelDto.getName())) {
            throw new IllegalArgumentException("Ya existe un hotel con el nombre informado");
        }

        Hotel hotel = HotelMapping.hotelDtoToHotel(hotelDto);
        return hotelRepository.save(hotel);
    };

    public HotelDto update(Long id, HotelUpdateDto hotelUpdateDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID" + id));

        Optional.ofNullable(hotelUpdateDto.getName()).ifPresent(hotel::setName);
        Optional.ofNullable(hotelUpdateDto.getLocation()).ifPresent(hotel::setLocation);
        Optional.ofNullable(hotelUpdateDto.getFeatures())
                .filter(features -> !features.isEmpty())
                .ifPresent(hotel::setFeatures);
        Optional.ofNullable(hotelUpdateDto.getDescription()).ifPresent(hotel::setDescription);
        Optional.ofNullable(hotelUpdateDto.getPrice()).ifPresent(hotel::setPrice);
        Optional.ofNullable(hotelUpdateDto.getScore()).ifPresent(hotel::setScore);
        Optional.ofNullable(hotelUpdateDto.getPhone()).ifPresent(hotel::setPhone);
        Optional.ofNullable(hotelUpdateDto.getEmail()).ifPresent(hotel::setEmail);

        Optional.ofNullable(hotelUpdateDto.getCategory()).ifPresent(description -> {
            Category category = categoryRepository.findByDescription(description);
            if(category != null) {
                hotel.setCategory(category);
            }
        });

        return HotelMapping.hotelToHotelDto(hotelRepository.save(hotel));

    }

    public Boolean delete(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID" + id));

         hotelRepository.delete(hotel);

         return true;

    };

    public Page<HotelDto> getAllRandom(String seed, int page, int size) {
        int offset = page * size;

        List<HotelDto> hotels = hotelRepository.findAllRandom(seed, offset, size)
                .stream()
                .map(HotelMapping::hotelToHotelDto)
                .collect(Collectors.toList());

        long total = hotelRepository.count();

        return new PageImpl<>(hotels, PageRequest.of(page, size), total);
    }

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
