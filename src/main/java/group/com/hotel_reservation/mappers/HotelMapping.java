package group.com.hotel_reservation.mappers;

import group.com.hotel_reservation.models.dto.hotel.HotelCreateDto;
import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import group.com.hotel_reservation.models.dto.hotel.HotelImageDto;
import group.com.hotel_reservation.models.dto.hotelPolicies.HotelPolicyDto;
import group.com.hotel_reservation.models.entities.Hotel;
import group.com.hotel_reservation.models.entities.HotelImage;

import java.util.List;
import java.util.stream.Collectors;

public class HotelMapping {

    public static HotelDto hotelToHotelDto(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();

        hotelDto.setId(hotel.getId());
        hotelDto.setName(hotel.getName());
        hotelDto.setLocation(hotel.getLocation());
        hotelDto.setFeatures(hotel.getFeatures());
        hotelDto.setPrice(hotel.getPrice());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setScore(hotel.getScore());
        hotelDto.setPhone(hotel.getPhone());
        hotelDto.setEmail(hotel.getEmail());
        hotelDto.setCategory(hotel.getCategory().getDescription());

        List<HotelPolicyDto> policies = hotel.getPolicies()
                .stream()
                .map(HotelPolicyMapping::hotelPolicyToDto)
                .toList();

        hotelDto.setPolicies(policies);

        List<HotelImageDto> images = hotel.getImages()
                .stream()
                .map(image -> {
                    HotelImageDto dto = new HotelImageDto();
                    dto.setUrl(image.getUrl());
                    return dto;
                })
                .collect(Collectors.toList());

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

        List<HotelImage> images = hotelDto.getImages()
                .stream()
                .map(image -> {
                    HotelImage newImage = new HotelImage();
                    newImage.setUrl(image.getUrl());
                    newImage.setHotel(hotel);
                    return newImage;
                })
                .collect(Collectors.toList());

        hotel.setImages(images);

        return hotel;
    }

    public static Hotel hotelCreateDtoToHotel(HotelCreateDto hotelDto) {
        Hotel hotel = new Hotel();

        hotel.setName(hotelDto.getName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setPrice(hotelDto.getPrice());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setScore(hotelDto.getScore());
        hotel.setPhone(hotelDto.getPhone());
        hotel.setEmail(hotelDto.getEmail());

        List<HotelImage> images = hotelDto.getImages()
                .stream()
                .map(image -> {
                    HotelImage newImage = new HotelImage();
                    newImage.setUrl(image.getUrl());
                    newImage.setHotel(hotel);
                    return newImage;
                })
                .collect(Collectors.toList());

        hotel.setImages(images);

        // ----> TO DO: resolver FEATURES

        return hotel;
    }
}
