package group.com.hotel_reservation.models.entities;

import group.com.hotel_reservation.models.entities.Hotel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private Integer guests;
    private Integer nights;
    private Double totalPrice;

    private LocalDate bookedFrom;
    private LocalDate bookedTo;
    private LocalDate createdAt;


}