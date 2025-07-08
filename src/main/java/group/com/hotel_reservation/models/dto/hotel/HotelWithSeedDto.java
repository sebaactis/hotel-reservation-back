package group.com.hotel_reservation.models.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class HotelWithSeedDto<T> {
    private String seed;
    private Page<T> page;
}
