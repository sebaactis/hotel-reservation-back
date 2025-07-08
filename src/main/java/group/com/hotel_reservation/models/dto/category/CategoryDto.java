package group.com.hotel_reservation.models.dto.category;

import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    @NotNull
    private Long id;

    @NotNull
    private String description;

    @NotNull
    private List<HotelDto> hotels;
}
