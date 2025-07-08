package group.com.hotel_reservation.models.dto.hotel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @Size(min = 1, max = 25)
    private String location;

    @NotNull
    private Set<String> features = new HashSet<>();

    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Float score;

    @NotNull
    private Long phone;

    @NotNull
    private String email;

    private String category;

    private List<HotelImageDto> images;
}
