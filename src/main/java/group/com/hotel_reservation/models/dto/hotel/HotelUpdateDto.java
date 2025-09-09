package group.com.hotel_reservation.models.dto.hotel;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelUpdateDto {

    @Length(min = 5, max = 25)
    private String name;

    @Length(min = 5, max = 25)
    private String location;

    private Set<String> features = new HashSet<>();

    @Length(min = 15, max = 200)
    private String description;

    @Min(1)
    private BigDecimal price;
    private Float score;
    private Long phone;
    private String email;
    private String category;
    private List<HotelImageDto> images;
}
