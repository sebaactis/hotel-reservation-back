package group.com.hotel_reservation.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @ElementCollection
    @CollectionTable(name = "hotel_features", joinColumns = @JoinColumn(name = "hotel_id"))
    @Column(name = "feature_name")
    private Set<String> features = new HashSet<>();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Long phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelImage> images = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
}
