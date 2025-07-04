package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.HotelDto;
import group.com.hotel_reservation.models.dto.HotelImageDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelMapping {

    public static HotelDto hotelToHotelDto(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        List<HotelImageDto> images = new ArrayList<>();
        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setLocation(hotel.getLocation());
        hotelDto.setFeatures(hotel.getFeatures());
        hotelDto.setPrice(hotel.getPrice());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setScore(hotel.getScore());
        hotelDto.setPhone(hotel.getPhone());
        hotelDto.setEmail(hotel.getEmail());

        for(HotelImage hotelImage : hotel.getImages()) {

            HotelImageDto hotelImageDto = new HotelImageDto();
            hotelImageDto.setUrl(hotelImage.getUrl());
            images.add(hotelImageDto);
        }

        hotelDto.setImages(images);

        return hotelDto;
    }

    public static Hotel hotelDtoToHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel();

        hotel.setId(hotelDto.getId());
        hotel.setName(hotelDto.getName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setFeatures(hotelDto.getFeatures());
        hotel.setPrice(hotelDto.getPrice());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setScore(hotelDto.getScore());
        hotel.setPhone(hotelDto.getPhone());
        hotel.setEmail(hotelDto.getEmail());

        List<HotelImage> images = new ArrayList<>();

        for (HotelImageDto imageDto : hotelDto.getImages()) {
            HotelImage image = new HotelImage();
            image.setUrl(imageDto.getUrl());
            image.setHotel(hotel);
            images.add(image);
        }
        hotel.setImages(images);

        return hotel;
    }
}
