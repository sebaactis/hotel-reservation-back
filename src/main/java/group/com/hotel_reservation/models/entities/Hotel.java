package group.com.hotel_reservation.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
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
    @MapKeyColumn(name = "feature_name")
    @Column(name = "icon", nullable = false)
    private Map<String, String> features = new HashMap<>();

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private BigDecimal discount;
}
