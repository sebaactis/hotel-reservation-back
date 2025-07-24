package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.hotelPolicies.HotelPolicyDto;
import group.com.hotel_reservation.models.entities.HotelPolicy;

public class HotelPolicyMapping {

    public static HotelPolicyDto hotelPolicyToDto(HotelPolicy hotelPolicy) {
        HotelPolicyDto hotelPolicyDto = new HotelPolicyDto();

        hotelPolicyDto.setId(hotelPolicy.getId());
        hotelPolicyDto.setTitle(hotelPolicy.getTitle());
        hotelPolicyDto.setDescription(hotelPolicy.getDescription());

        return hotelPolicyDto;
    }
}
