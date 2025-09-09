package group.com.hotel_reservation.models.dto.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelCreateDto {

    @NotNull
    @Length (min = 5, max = 25)
    private String name;

    @NotNull
    @Length (min = 5, max = 25)
    private String location;

    @NotNull
    @Length (min = 15, max = 200)
    private String description;

    private List<HotelImageDto> images;

    @NotNull
    @Min(1)
    private BigDecimal price;

    @NotNull
    private Float score;

    @NotNull
    private Long phone;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String category;

    @NotNull
    private List<Long> featureIds;
}
