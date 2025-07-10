package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public class RegisterResponse {

    @NotNull
    public String email;
}
