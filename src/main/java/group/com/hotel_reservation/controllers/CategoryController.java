package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.entities.Category;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> findAll() {

        try {
            List<CategoryDto> categories = service.findAll();

            if(categories.isEmpty()) {
                ApiResponse<List<CategoryDto>> response = new ApiResponse<>("No hay categorias cargadas", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<List<CategoryDto>> response = new ApiResponse<>("Categorias encontradas exitosamente", HttpStatus.OK.toString(), categories);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<List<CategoryDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("{description}")
    public ResponseEntity<ApiResponse<CategoryDto>> findByName(@PathVariable String description) {

        try {
            CategoryDto category = service.findByDescription(description);

            if(category == null) {
                ApiResponse<CategoryDto> response = new ApiResponse<>("No se encuentra la categoria con nombre: " + description, HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<CategoryDto> response = new ApiResponse<>("Categoria encontrada exitosamente", HttpStatus.OK.toString(), category);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<CategoryDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
