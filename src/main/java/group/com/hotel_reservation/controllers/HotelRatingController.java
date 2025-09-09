package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDetailsDto;
import group.com.hotel_reservation.models.dto.hotelRating.HotelRatingDto;
import group.com.hotel_reservation.models.dto.hotelRating.RateHotelDto;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.hotel.HotelRatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/rating")
public class HotelRatingController {

    private final HotelRatingService hotelRatingService;

    public HotelRatingController(HotelRatingService hotelRatingService) {
        this.hotelRatingService = hotelRatingService;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{userId}/{hotelId}")
    public ResponseEntity<ApiResponse<HotelRatingDto>> rateHotel(@PathVariable Long userId, @PathVariable Long hotelId, @Valid @RequestBody RateHotelDto dto) {

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

    @GetMapping("{hotelId}")
    public ResponseEntity<ApiResponse<List<HotelRatingDetailsDto>>> getRatingByHotelId(@PathVariable Long hotelId) {
        try {
            List<HotelRatingDetailsDto> ratings = hotelRatingService.getHotelRatingsById(hotelId);

            if(ratings.isEmpty()) {
                ApiResponse<List<HotelRatingDetailsDto>> response = new ApiResponse<>("No hay ratings para este hotel", HttpStatus.OK.toString(), null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            ApiResponse<List<HotelRatingDetailsDto>> response = new ApiResponse<>("Ratings encontrados", HttpStatus.OK.toString(), ratings);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<List<HotelRatingDetailsDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }




}
