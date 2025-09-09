package group.com.hotel_reservation.models.dto.feature;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureUpdateDto {

    @NotNull
    @Length(min = 3, max = 20)
    private String name;

    @NotNull
    private String icon;
}
