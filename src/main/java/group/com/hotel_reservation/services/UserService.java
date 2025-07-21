package group.com.hotel_reservation.services;

import group.com.hotel_reservation.mappers.UserMapping;
import group.com.hotel_reservation.models.dto.user.UpdateRolDto;
import group.com.hotel_reservation.models.dto.user.UserDto;
import group.com.hotel_reservation.models.entities.Role;
import group.com.hotel_reservation.models.entities.User;
import group.com.hotel_reservation.persistence.repositories.RoleRepository;
import group.com.hotel_reservation.persistence.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapping::userToUserDto).toList();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User no encontrado"));
    }

    public UserDto changeRole(UpdateRolDto updateRolDto) {
        User user = userRepository.findByEmail(updateRolDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Role newRole = roleRepository.findByName(updateRolDto.getRole())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        user.setRole(newRole);
        User userUpdate = userRepository.save(user);

        return UserMapping.userToUserDto(userUpdate);
    }

}
