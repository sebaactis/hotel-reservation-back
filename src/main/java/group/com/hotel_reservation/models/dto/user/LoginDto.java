package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
