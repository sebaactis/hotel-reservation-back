package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.HotelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<HotelDto>>  create(@Valid @RequestBody HotelDto hotelDto) {
        try {
            Hotel hotelToCreate = hotelService.create(hotelDto);

            if(hotelToCreate == null) {
                ApiResponse<HotelDto> response = new ApiResponse<>("No se pudo crear el hotel", HttpStatus.BAD_REQUEST.toString(), hotelDto);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelDto> response = new ApiResponse<>("Hotel creado correctamente", HttpStatus.CREATED.toString(), hotelDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<HotelDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), hotelDto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
