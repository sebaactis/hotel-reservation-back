package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.hotelPolicies.HotelPolicyDto;
import group.com.hotel_reservation.models.dto.hotelPolicies.SaveHotelPolicyDto;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.hotel.HotelPolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotelPolicy")
public class HotelPolicyController {
    private final HotelPolicyService hotelPolicyService;

    public HotelPolicyController(HotelPolicyService hotelPolicyService) {
        this.hotelPolicyService = hotelPolicyService;
    }

    @GetMapping("{hotelId}")
    public ResponseEntity<ApiResponse<List<HotelPolicyDto>>> getPolicies(@PathVariable Long hotelId) {
        try {
            List<HotelPolicyDto> policies = hotelPolicyService.getPoliciesByHotel(hotelId);

            if(policies.isEmpty()) {
                ApiResponse<List<HotelPolicyDto>> response = new ApiResponse<>("El hotel proporcionado no tiene politicas", HttpStatus.OK.toString(), null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            ApiResponse<List<HotelPolicyDto>> response = new ApiResponse<>("Polícitas del hotel recuperadas con éxito", HttpStatus.OK.toString(), policies);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<List<HotelPolicyDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("{hotelId}")
    public ResponseEntity<ApiResponse<HotelPolicyDto>> createPolicy(@PathVariable Long hotelId, @Valid @RequestBody SaveHotelPolicyDto dto) {
        try {
            HotelPolicyDto creation = hotelPolicyService.createPolicy(hotelId, dto);

            if(creation == null) {
                ApiResponse<HotelPolicyDto> response = new ApiResponse<>("No se pudo crear la politica", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelPolicyDto> response = new ApiResponse<>("Politica creada correctamente", HttpStatus.CREATED.toString(), creation);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<HotelPolicyDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/{policyId}")
    public ResponseEntity<ApiResponse<HotelPolicyDto>> updatePolicy(@PathVariable Long policyId, @RequestBody SaveHotelPolicyDto dto) {
        try {
            HotelPolicyDto update = hotelPolicyService.updatePolicy(policyId, dto);

            if(update == null) {
                ApiResponse<HotelPolicyDto> response = new ApiResponse<>("No se pudo editar la politica", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<HotelPolicyDto> response = new ApiResponse<>("Politica editada correctamente", HttpStatus.OK.toString(), update);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<HotelPolicyDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{policyId}")
    public ResponseEntity<ApiResponse<Boolean>> deletePolicy(@PathVariable Long policyId) {
        try {
            Boolean delete = hotelPolicyService.deletePolicy(policyId);

            if(!delete) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se pudo eliminar la politica", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Boolean> response = new ApiResponse<>("Politica eliminada correctamente", HttpStatus.OK.toString(), delete);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
