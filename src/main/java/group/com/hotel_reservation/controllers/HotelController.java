package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.HotelService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<HotelDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
                try {
                    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
                    Pageable pageable = PageRequest.of(page, size, sort);
                    Page<HotelDto> hotels = hotelService.getAll(pageable);

                    if(hotels.isEmpty()) {
                        ApiResponse<Page<HotelDto>> response = new ApiResponse<>("No hay hoteles cargados", HttpStatus.BAD_REQUEST.toString(), hotels);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                    }

                    ApiResponse<Page<HotelDto>> response = new ApiResponse<>("Hoteles recuperados correctamente", HttpStatus.OK.toString(), hotels);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } catch (Exception e) {
                    ApiResponse<Page<HotelDto>> response = new ApiResponse<>("Error al intentar recuperar los hoteles", HttpStatus.BAD_REQUEST.toString(), null);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
    };

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HotelDto>> getById(@PathVariable Long id) {
        try {
            HotelDto hotel = hotelService.getOne(id);

            if (hotel == null) {
                ApiResponse<HotelDto> response = new ApiResponse<>("No se encuentra el hotel con el id proporcionado", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelDto> response = new ApiResponse<>("Hotel encontrado correctamente", HttpStatus.OK.toString(), hotel);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<HotelDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    };

    @GetMapping("/random")
    public ResponseEntity<ApiResponse<List<HotelDto>>> getRandomHotels() {
        try {
            List<HotelDto> hotels = hotelService.getRandomHotels();

            if(hotels.isEmpty()) {
                ApiResponse<List<HotelDto>> response = new ApiResponse<>("No hay hoteles cargados", HttpStatus.BAD_REQUEST.toString(), hotels);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<List<HotelDto>> response = new ApiResponse<>("Hoteles recuperados correctamente", HttpStatus.OK.toString(), hotels);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<HotelDto>> response = new ApiResponse<>("Error al intentar recuperar los hoteles", HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    };

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
            ApiResponse<HotelDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> deleteById(@PathVariable Long id) {
        try {
            Boolean delete = hotelService.delete(id);

            if(!delete) {
                ApiResponse<String> response = new ApiResponse<>("No se pudo borrar el hotel", HttpStatus.BAD_REQUEST.toString(), "");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<String> response = new ApiResponse<>("Hotel borrado correctamente", HttpStatus.OK.toString(), "");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), "");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
