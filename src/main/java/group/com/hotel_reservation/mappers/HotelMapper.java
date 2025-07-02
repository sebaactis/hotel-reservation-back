package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.entities.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toEntity(HotelDto hotelDto);
    HotelDto toDto(Hotel hotel);
}
