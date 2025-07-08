package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.category.CategoryDto;
import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.entities.Category;

import java.util.List;
import java.util.Optional;

public class CategoryMapping {

    public static CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setDescription(category.getDescription());

        List<HotelDto> hotels = category.getHotels().stream().map(HotelMapping::hotelToHotelDto).toList();
        categoryDto.setHotels(hotels);

        return categoryDto;
    };

}
