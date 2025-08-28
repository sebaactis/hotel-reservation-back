package group.com.hotel_reservation.services.hotel;

import group.com.hotel_reservation.mappers.HotelMapping;
import group.com.hotel_reservation.models.dto.hotel.HotelCreateDto;
import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.dto.hotel.HotelUpdateDto;
import group.com.hotel_reservation.models.entities.Category;
import group.com.hotel_reservation.models.entities.Feature;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.category.CategoryRepository;
import group.com.hotel_reservation.persistence.repositories.feature.FeatureRepository;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CategoryRepository categoryRepository;
    private final FeatureRepository featureRepository;

    public HotelService(HotelRepository hotelRepository, CategoryRepository categoryRepository, FeatureRepository featureRepository) {
        this.hotelRepository = hotelRepository;
        this.categoryRepository = categoryRepository;
        this.featureRepository = featureRepository;
    }

    public HotelDto create(HotelCreateDto hotelDto) {
        if (hotelRepository.existsByName(hotelDto.getName())) {
            throw new IllegalArgumentException("Ya existe un hotel con el nombre informado");
        }

        Hotel hotel = HotelMapping.hotelCreateDtoToHotel(hotelDto);
        hotel.setCategory(categoryRepository.findByDescription(hotelDto.getCategory()));

        if(hotelDto.getFeatureIds() != null) {
            Set<Feature> features = resolveFeaturesByIds(hotelDto.getFeatureIds());
            hotel.setFeatures(features);
        }

        Hotel saved = hotelRepository.save(hotel);
        return HotelMapping.hotelToHotelDto(saved);

    };

    public HotelDto update(Long id, HotelUpdateDto hotelUpdateDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID" + id));

        Optional.ofNullable(hotelUpdateDto.getName()).ifPresent(hotel::setName);
        Optional.ofNullable(hotelUpdateDto.getLocation()).ifPresent(hotel::setLocation);

        // ----> TO DO: resolver FEATURES

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

        Set<String> incomingNames = hotelUpdateDto.getFeatures();

        if(incomingNames != null) {
            Set<Feature> resolved = resolveFeaturesByNames(incomingNames);

            hotel.getFeatures().clear();
            hotel.getFeatures().addAll(resolved);
        }

        Hotel saved = hotelRepository.save(hotel);
        return HotelMapping.hotelToHotelDto(saved);
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

    public Page<HotelDto> getAll( Pageable pageable) {
        Page<Hotel> hotels = hotelRepository.findAll(pageable);
        return hotels.map(HotelMapping::hotelToHotelDto);
    }

    public Page<HotelDto> search(String location, LocalDate from, LocalDate to, Pageable pageable) {
        return hotelRepository.search(location, from, to, pageable)
                .map(HotelMapping::hotelToHotelDto);
    }

    public HotelDto getOne(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID"));

        return HotelMapping.hotelToHotelDto(hotel);
    }

    public Hotel getFullHotel(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID"));

    }

    public List<HotelDto> getRandomHotels() {
        return hotelRepository.getRandomsHotels().stream()
                .map(HotelMapping::hotelToHotelDto)
                .collect(Collectors.toList());
    }


    private Set<Feature> resolveFeaturesByIds(List<Long> featureIds) {
        if (featureIds == null) return Collections.emptySet();

        List<Feature> found = featureRepository.findAllById(featureIds);

        if (found.size() != featureIds.size()) {

            Set<Long> foundIds = found.stream().map(Feature::getId).collect(Collectors.toSet());

            List<Long> missing = featureIds.stream().filter(id -> !foundIds.contains(id)).toList();

            throw new IllegalArgumentException("Features no encontradas para IDs: " + missing);
        }

        return new HashSet<>(found);
    }

    private Set<Feature> resolveFeaturesByNames(Set<String> names) {
        if (names == null) return null;
        if (names.isEmpty()) return Collections.emptySet();

        Set<String> normalized = names.stream()
                .filter(Objects::nonNull)
                .map(s -> s.trim().toLowerCase())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        if (normalized.isEmpty()) return Collections.emptySet();

        Set<Feature> found = featureRepository.findByNameInIgnoreCase(normalized);

        Set<String> foundNames = found.stream()
                .map(f -> f.getName().toLowerCase())
                .collect(Collectors.toSet());

        Set<String> missing = new HashSet<>(normalized);

        missing.removeAll(foundNames);

        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("Features no encontradas para nombres: " + missing);
        }

        return found;
    }
}
