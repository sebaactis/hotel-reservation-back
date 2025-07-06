package group.com.hotel_reservation.models.dto;

import group.com.hotel_reservation.models.entities.HotelImage;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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

    private List<HotelImageDto> images;
}
