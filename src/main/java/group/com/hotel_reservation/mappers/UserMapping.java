package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.user.UserDto;
import group.com.hotel_reservation.models.entities.User;

public class UserMapping {

    public static UserDto userToUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole().getName());

        return userDto;
    }
}
