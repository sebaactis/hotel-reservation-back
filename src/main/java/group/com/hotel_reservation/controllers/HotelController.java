package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
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
    public ResponseEntity<HotelDto> create(@Valid @RequestBody HotelDto hotelDto) {
        try {
            Hotel hotelToCreate = hotelService.create(hotelDto);

            if(hotelToCreate == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hotelDto);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(hotelDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(hotelDto);
        }
    }
}
