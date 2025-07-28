package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    @NotNull
    private String email;

    @NotNull
    private Long userId;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String token;
}
