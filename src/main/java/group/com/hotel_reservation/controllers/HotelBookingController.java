package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingDto;
import group.com.hotel_reservation.models.dto.hotelBooking.HotelBookingRequest;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.hotel.HotelBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotelBooking")
public class HotelBookingController {
    private final HotelBookingService hotelBookingService;

    public HotelBookingController(HotelBookingService hotelBookingService) {
        this.hotelBookingService = hotelBookingService;
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<ApiResponse<List<HotelBookingDto>>> getByHotelId(@PathVariable Long id) {

        try {
            List<HotelBookingDto> hotelBookings = hotelBookingService.findByHotelId(id);

            if(hotelBookings.isEmpty()) {
                ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>("Este hotel no tiene reservas", HttpStatus.OK.toString(), null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>("Reservas recuperadas", HttpStatus.OK.toString(), hotelBookings);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<List<HotelBookingDto>>> getByUserId(@PathVariable Long id) {

        try {
            List<HotelBookingDto> hotelBookings = hotelBookingService.findByUserId(id);

            if(hotelBookings.isEmpty()) {
                ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>("Este usuario no tiene reservas", HttpStatus.OK.toString(), null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>("Reservas recuperadas", HttpStatus.OK.toString(), hotelBookings);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch(Exception e) {
            ApiResponse<List<HotelBookingDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<HotelBookingDto>> create(@RequestBody HotelBookingRequest request) {
        try {
            HotelBookingDto hotelBooking = hotelBookingService.createBooking(request);

            if(hotelBooking.getId() == null) {
                ApiResponse<HotelBookingDto> response = new ApiResponse<>("No se pudo crear la reserva", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelBookingDto> response = new ApiResponse<>("Reserva creada correctamente", HttpStatus.CREATED.toString(), hotelBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(Exception e) {
            ApiResponse<HotelBookingDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
