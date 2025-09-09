package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.user.UpdateRolDto;
import group.com.hotel_reservation.models.dto.user.UserDto;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserDto>>> getAll() {
        try {
            List<UserDto> users = userService.getAll();

            if(users.isEmpty()) {
                ApiResponse<List<UserDto>> response = new ApiResponse<>("No hay usuarios actualmente", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<List<UserDto>> response = new ApiResponse<>("Usuarios recuperados", HttpStatus.OK.toString(), users);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<List<UserDto>> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("updateRole")
    public ResponseEntity<ApiResponse<UserDto>> updateRole(@Valid @RequestBody UpdateRolDto updateRolDto) {
        try {
            UserDto userDto = userService.changeRole(updateRolDto);

            if (userDto.getEmail() == null) {
                ApiResponse<UserDto> response = new ApiResponse<>("No se pudo actualizar el role del usuario", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<UserDto> response = new ApiResponse<>("Se actualizó el role del usuario", HttpStatus.OK.toString(), userDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<UserDto> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    };
}
