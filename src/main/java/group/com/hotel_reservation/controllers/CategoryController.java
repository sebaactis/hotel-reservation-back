package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.dto.category.SaveCategoryDto;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryDto>> create(@RequestBody SaveCategoryDto saveCategoryDto) {
        try {
            CategoryDto category = service.createCategory(saveCategoryDto);

            if(category == null) {
                ApiResponse<CategoryDto> response = new ApiResponse<>("No se pudo crear la categoria", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<CategoryDto> response = new ApiResponse<>("Categoria creada correctamente", HttpStatus.CREATED.toString(), category);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<CategoryDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> edit(@PathVariable Long id, @RequestBody SaveCategoryDto saveCategoryDto) {
        try {
            CategoryDto category = service.editCategory(id, saveCategoryDto);

            if(category == null) {
                ApiResponse<CategoryDto> response = new ApiResponse<>("No se pudo editar la categoria", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<CategoryDto> response = new ApiResponse<>("Categoria editada correctamente", HttpStatus.OK.toString(), category);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<CategoryDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        try {
            Boolean deleteCategory = service.deleteCategory(id);

            if(!deleteCategory) {
                ApiResponse<Boolean> response = new ApiResponse<>("No se pudo borrar la categoria", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<Boolean> response = new ApiResponse<>("Categoria borrada correctamente", HttpStatus.OK.toString(), deleteCategory);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<Boolean> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
