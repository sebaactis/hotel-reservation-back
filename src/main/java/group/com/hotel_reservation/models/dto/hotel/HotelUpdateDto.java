package group.com.hotel_reservation.models.dto.hotel;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelUpdateDto {

    @Size(min = 1, max = 25)
    private String name;

    @Size(min = 1, max = 25)
    private String location;
    private Set<String> features = new HashSet<>();

    @Size(min = 1, max = 100)
    private String description;

    private BigDecimal price;
    private Float score;
    private Long phone;
    private String email;
    private String category;
    private List<HotelImageDto> images;
}
