package group.com.hotel_reservation.models.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SaveCategoryDto {
    @NotNull
    @Length(min = 4, max = 15)
    private String description;
}
