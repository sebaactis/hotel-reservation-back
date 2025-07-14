package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRolDto {
    @NotNull
    public String email;

    @NotNull
    public String role;
}
