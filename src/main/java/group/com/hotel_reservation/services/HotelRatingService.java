package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.HotelRatingMapping;
import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDetailsDto;
import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelRating;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.HotelRatingRepository;
import group.com.hotel_reservation.persistence.repositories.HotelRepository;
import group.com.hotel_reservation.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRatingService {

    private final HotelRepository hotelRepository;
    private final HotelRatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final HotelRatingMapping hotelRatingMapping;

    public HotelRatingService(HotelRepository hotelRepository, HotelRatingRepository ratingRepository, UserRepository userRepository, HotelRatingMapping hotelRatingMapping) {
        this.hotelRepository = hotelRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.hotelRatingMapping = hotelRatingMapping;
    }

    public HotelRatingDto rateHotel(Long userId, Long hotelId, Float score, String comment) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        HotelRating rating = new HotelRating();
        rating.setHotel(hotel);
        rating.setUser(user);
        rating.setScore(score);
        rating.setComment(comment);

        Double average = ratingRepository.findAverageScoreByHotelId(hotelId);
        hotel.setScore(average != null ? average.floatValue() : score);
        hotelRepository.save(hotel);

        return hotelRatingMapping.hotelRatingToDto(ratingRepository.save(rating));
    }

    public List<HotelRatingDetailsDto> getHotelRatingsById(Long hotelId) {
        List<HotelRating> ratings = ratingRepository.findByHotelId(hotelId);
        return ratings.stream()
                .map(hotelRatingMapping::hotelRatingToDetailsDto)
                .toList();
    }

    public Double getHotelAverageScore(Long hotelId) {
        return ratingRepository.findAverageScoreByHotelId(hotelId);
    }
}
