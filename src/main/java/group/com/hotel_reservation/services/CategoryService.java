package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.CategoryMapping;
import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.entities.Category;
import group.com.hotel_reservation.persistence.repositories.CategoryRepository;
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
}
