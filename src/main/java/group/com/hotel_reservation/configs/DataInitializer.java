package group.com.hotel_reservation.configs;

import group.com.hotel_reservation.models.entities.Role;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.role.RoleRepository;
import group.com.hotel_reservation.persistence.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Role adminRole = new Role();
        adminRole.setName("ADMIN");

        Role userRole = new Role();
        userRole.setName("USER");

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(userRole);
        }



        Role rolePersistent = roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("Error al recuperar el rol ADMIN"));
        userRepository.findByEmail("admin@demo.com").ifPresentOrElse(
                user -> {

                },
                () -> {
                    User admin = new User();
                    admin.setEmail("admin@demo.com");
                    admin.setName("Admin");
                    admin.setLastName("System");
                    admin.setPassword(passwordEncoder.encode("123456"));
                    admin.setRole(rolePersistent);

                    userRepository.save(admin);

                    System.out.println("✅ Usuario ADMIN creado: admin@demo.com / 123456");
                }
        );
    }
}
