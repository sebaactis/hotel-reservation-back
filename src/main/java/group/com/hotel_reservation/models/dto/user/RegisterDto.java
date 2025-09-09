package group.com.hotel_reservation.models.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    @Length(min = 8, max = 20)
    private String password;

    @NotNull
    @Length(min = 8, max = 20)
    private String confirmPassword;
}
