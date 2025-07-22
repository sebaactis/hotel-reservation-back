package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDto;
import group.com.hotel_reservation.models.dto.hotelRating.RateHotelDto;
import group.com.hotel_reservation.models.entities.HotelRating;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.HotelRatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/rating")
public class HotelRatingController {

    private final HotelRatingService hotelRatingService;

    public HotelRatingController(HotelRatingService hotelRatingService) {
        this.hotelRatingService = hotelRatingService;
    }

    @PostMapping("/{userId}/{hotelId}")
    public ResponseEntity<ApiResponse<HotelRatingDto>> rateHotel(@PathVariable Long userId, @PathVariable Long hotelId, @RequestBody RateHotelDto dto) {

        try {
            HotelRatingDto rating = hotelRatingService.rateHotel(userId, hotelId, dto.getScore(), dto.getComment());

            if(rating == null) {
                ApiResponse<HotelRatingDto> response = new ApiResponse<>("No se pudo cargar la reseña", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelRatingDto> response = new ApiResponse<>("Reseña cargada correctamente", HttpStatus.CREATED.toString(), rating);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<HotelRatingDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
