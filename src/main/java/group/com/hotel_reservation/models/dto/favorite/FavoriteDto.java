package group.com.hotel_reservation.models.dto.favorite;

import group.com.hotel_reservation.models.dto.hotel.HotelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    private Long id;
    private Long userId;
    private HotelDto hotelDto;
    private Date createdAt;
}
