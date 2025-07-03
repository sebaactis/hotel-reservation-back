package group.com.hotel_reservation.models.dto;

import group.com.hotel_reservation.models.entities.HotelImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotelDto {

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @NotNull
    @Size(min = 1, max = 25)
    private String location;

    @NotNull
    private Map<String, String> features = new HashMap<>();

    @NotNull
    @Size(min = 1, max = 100)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Float score;

    @NotNull
    private BigDecimal discount;

    private List<HotelImageDto> images;
}
