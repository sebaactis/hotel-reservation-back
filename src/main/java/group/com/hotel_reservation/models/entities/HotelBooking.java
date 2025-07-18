package group.com.hotel_reservation.models.entities;

import group.com.hotel_reservation.models.entities.Hotel;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private LocalDate bookedFrom;
    private LocalDate bookedTo;
}