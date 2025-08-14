package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class MeResponse {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String role;

    @NotNull
    private String userId;
}
