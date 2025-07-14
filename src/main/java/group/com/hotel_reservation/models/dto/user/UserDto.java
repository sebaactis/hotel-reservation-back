package group.com.hotel_reservation.models.dto.user;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String role;
}
