package group.com.hotel_reservation.models.dto.hotelRating;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
public class RateHotelDto {

    @NotNull()
    @Range(min = 0, max = 5)
    private Float score;

    @NotNull()
    @Length(min = 5, max = 50)
    private String comment;
}