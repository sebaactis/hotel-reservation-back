package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeDto {
    @NotNull
    private String token;
}
