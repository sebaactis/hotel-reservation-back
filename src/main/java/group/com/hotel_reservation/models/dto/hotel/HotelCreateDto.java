package group.com.hotel_reservation.models.dto.hotel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelCreateDto {

    @NotNull
    @Size(min=1, max=25)
    private String name;

    @NotNull @Size(min=1, max=25)
    private String location;

    @NotNull @Size(min=1, max=100)
    private String description;

    private List<HotelImageDto> images;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Float score;

    @NotNull
    private Long phone;

    @NotNull
    private String email;

    @NotNull
    private String category;

    @NotNull
    private List<Long> featureIds;
}
