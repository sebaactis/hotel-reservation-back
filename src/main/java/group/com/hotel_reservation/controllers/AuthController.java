package group.com.hotel_reservation.controllers;

import group.com.hotel_reservation.models.dto.user.*;
import group.com.hotel_reservation.responses.ApiResponse;
import group.com.hotel_reservation.services.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody @Valid RegisterDto registerDto) {
        try {
            RegisterResponse register = authService.register(registerDto);

            if(register.email == null) {
                ApiResponse<RegisterResponse> response = new ApiResponse<>("No se pudo registrar el usuario", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<RegisterResponse> response = new ApiResponse<>("Usuario registrado correctamente", HttpStatus.CREATED.toString(), register);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            ApiResponse<RegisterResponse> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody @Valid LoginDto loginDto) {
        try {
            AuthResponse login = authService.login(loginDto);

            if(login.getEmail() == null) {
                ApiResponse<AuthResponse> response = new ApiResponse<>("Login incorrecto", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<AuthResponse> response = new ApiResponse<>("Login exitoso!", HttpStatus.OK.toString(), login);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<AuthResponse> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("me")
    public ResponseEntity<ApiResponse<MeResponse>> getMeInfo(@RequestBody MeDto dto) {
        try {
            MeResponse claims = authService.getMe(dto.getToken());

            if(claims.getEmail() == null) {
                ApiResponse<MeResponse> response = new ApiResponse<>("Error al intentar traer la informacion", HttpStatus.BAD_REQUEST.toString(), null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            ApiResponse<MeResponse> response = new ApiResponse<>("Informacion recuperada", HttpStatus.OK.toString(), claims);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            ApiResponse<MeResponse> response = new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.toString(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
