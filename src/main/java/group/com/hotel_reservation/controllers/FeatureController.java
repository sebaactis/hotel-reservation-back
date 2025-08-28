package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.feature.FeatureCreateDto;
import group.com.hotel_reservation.models.dto.feature.FeatureUpdateDto;
import group.com.hotel_reservation.models.entities.Feature;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.feature.FeatureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/feature")
public class FeatureController {

    private final FeatureService service;

    public FeatureController(FeatureService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<Feature>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "name") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        try {
            Page<Feature> features = service.getAll(pageable);

            if(features.isEmpty()) {
                ApiResponse<Page<Feature>> response = new ApiResponse<>("No hay features actualmente", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Page<Feature>> response = new ApiResponse<>("Features encontradas", HttpStatus.OK.toString(), features);
            return ResponseEntity.status(HttpStatus.OK).body(response);


        } catch (Exception e) {
            ApiResponse<Page<Feature>> response = new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<Feature>> getOne(@RequestParam Long id) {

        try {
            Feature feature = service.getOne(id);

            if(feature.getName().isEmpty()) {
                ApiResponse<Feature> response = new ApiResponse<>("No existe la feature consultada", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Feature> response = new ApiResponse<>("Feature encontrada", HttpStatus.OK.toString(), feature);
            return ResponseEntity.status(HttpStatus.OK).body(response);


        } catch (Exception e) {
            ApiResponse<Feature> response = new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Feature>> create(@RequestBody FeatureCreateDto featureCreateDto) {

        try {
            Feature feature = service.create(featureCreateDto);

            if(feature.getName().isEmpty()) {
                ApiResponse<Feature> response = new ApiResponse<>("Error al crear la feature", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Feature> response = new ApiResponse<>("Feature creada", HttpStatus.CREATED.toString(), feature);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);


        } catch (Exception e) {
            ApiResponse<Feature> response = new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Feature>> update(@RequestParam Long id, @RequestBody FeatureUpdateDto featureUpdateDto) {

        try {
            Feature feature = service.update(id, featureUpdateDto);

            if(feature.getName().isEmpty()) {
                ApiResponse<Feature> response = new ApiResponse<>("Error al editar la feature", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Feature> response = new ApiResponse<>("Feature editada", HttpStatus.OK.toString(), feature);
            return ResponseEntity.status(HttpStatus.OK).body(response);


        } catch (Exception e) {
            ApiResponse<Feature> response = new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Feature>> delete(@RequestParam Long id) {

        try {
            Boolean deleted = service.delete(id);

            if(!deleted) {
                ApiResponse<Feature> response = new ApiResponse<>("Error al eliminar la feature", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Feature> response = new ApiResponse<>("Feature eliminada", HttpStatus.OK.toString(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);


        } catch (Exception e) {
            ApiResponse<Feature> response = new ApiResponse<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
