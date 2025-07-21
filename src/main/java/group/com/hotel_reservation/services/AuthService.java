package group.com.hotel_reservation.services;

import group.com.hotel_reservation.models.dto.user.AuthResponse;
import group.com.hotel_reservation.models.dto.user.LoginDto;
import group.com.hotel_reservation.models.dto.user.RegisterDto;
import group.com.hotel_reservation.models.dto.user.RegisterResponse;
import group.com.hotel_reservation.models.entities.Role;
import group.com.hotel_reservation.models.entities.Token;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.RoleRepository;
import group.com.hotel_reservation.persistence.repositories.TokenRepository;
import group.com.hotel_reservation.persistence.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, TokenRepository tokenRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterDto registerDto) {

        if(userRepository.existsByEmail(registerDto.getEmail())) {
            throw new IllegalArgumentException("Email registrado, pruebe con otro");
        }

        if(!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found"));

        user.setRole(userRole);

        User userSave = userRepository.save(user);

        System.out.println(userSave);

        return RegisterResponse.builder()
                .email(registerDto.getEmail())
                .build();
    }

    public AuthResponse login(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());

        if(user.isEmpty() || !passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
            throw new IllegalArgumentException("Los datos son incorrectos");
        }

        String token = jwtService.generateToken(user.get().getEmail(), user.get().getRole());
        saveToken(token);

        return AuthResponse.builder()
                .userId(user.get().getId())
                .email(user.get().getEmail())
                .token(token)
                .build();
    }

    private void saveToken(String token) {
        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setExpirationDate(jwtService.extractClaims(token).getExpiration());
        tokenEntity.setRevoked(false);
        tokenEntity.setExpired(false);
        tokenRepository.save(tokenEntity);
    }
}
