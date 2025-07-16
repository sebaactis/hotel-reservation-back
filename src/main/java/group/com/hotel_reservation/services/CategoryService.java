package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.CategoryMapping;
import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.dto.category.SaveCategoryDto;
import group.com.hotel_reservation.models.entities.Category;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.persistence.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<CategoryDto> findAll() {
        List<Category> categories = repository.findAll();

        return categories.stream().map(CategoryMapping::categoryToDto).toList();
    }

    public CategoryDto findByDescription(String description) {
        Category category = repository.findByDescription(description);

        return CategoryMapping.categoryToDto(category);
    }

    public CategoryDto createCategory(SaveCategoryDto saveCategoryDto) {
        Category category = repository.findByDescription(saveCategoryDto.getDescription());

        if(category != null) {
            throw new IllegalArgumentException("Ya existe una categoria con este nombre");
        }

        Category newCategory = new Category();
        newCategory.setDescription(saveCategoryDto.getDescription());

        return CategoryMapping.categoryToDto(repository.save(newCategory));
    }

    public CategoryDto editCategory(Long id, SaveCategoryDto saveCategoryDto) {
        Category category = repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado con ID " + id));


        category.setDescription(saveCategoryDto.getDescription());
        return CategoryMapping.categoryToDto(repository.save(category));
    }

    public Boolean deleteCategory(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con ID " + id));

        repository.delete(category);

        return true;

    }
}
