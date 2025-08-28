package group.com.hotel_reservation.models.dto.feature;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureCreateDto {
    @NotNull
    private String name;

    @NotNull
    private String icon;
}
