package group.com.hotel_reservation.configs;

import group.com.hotel_reservation.models.entities.*;
import group.com.hotel_reservation.persistence.repositories.HotelImageRepository;
import group.com.hotel_reservation.persistence.repositories.category.CategoryRepository;
import group.com.hotel_reservation.persistence.repositories.feature.FeatureRepository;
import group.com.hotel_reservation.persistence.repositories.hotel.HotelRepository;
import group.com.hotel_reservation.persistence.repositories.role.RoleRepository;
import group.com.hotel_reservation.persistence.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final FeatureRepository featureRepository;
    private final HotelRepository hotelRepository;
    private final HotelImageRepository hotelImageRepository;


    @Override
    public void run(String... args) throws Exception {

        // Categories
        if(categoryRepository.count() == 0) {
            categoryRepository.save(new Category(null, "Estandar", null));
            categoryRepository.save(new Category(null, "Premium", null));
        }

        // Roles
        if(roleRepository.count() == 0) {
            roleRepository.save(new Role(null, "ADMIN"));
            roleRepository.save(new Role(null, "USER"));
        }

        // Features
        if(featureRepository.count() == 0) {
            featureRepository.save(new Feature(null, "Estacionamiento", "Car"));
        }

        // Hotel
        if (hotelRepository.count() == 0) {
            Category categoryPremium = categoryRepository.findByDescription("Premium");

            // 1) Guardás el hotel para obtener su id
            Hotel hotel = new Hotel(
                    null,
                    "Hotel Continental",
                    "Buenos Aires",
                    null,
                    "Hotel muy comodo y lujoso en pleno Buenos Aires",
                    new BigDecimal("200000"),
                    0f,
                    46782042L,
                    "hotelContinental@hotmail.com",
                    new ArrayList<>(), // lista vacía acá
                    null, null, null,
                    categoryPremium
            );
            hotel = hotelRepository.save(hotel);

            HotelImage img = new HotelImage();
            img.setUrl("https://zkctkoxxxsrxysjmqcpe.supabase.co/storage/v1/object/public/hotels/hotels/hotel-front_standard.webp");
            img.setHotel(hotel);

            hotelImageRepository.save(img);
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
