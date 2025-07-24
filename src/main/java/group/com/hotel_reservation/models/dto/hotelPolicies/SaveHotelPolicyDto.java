package group.com.hotel_reservation.models.dto.hotelPolicies;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SaveHotelPolicyDto {

    @NotNull
    @Length(min = 5, max = 20)
    private String title;

    @NotNull
    @Length(min = 15, max = 200)
    private String description;
}
