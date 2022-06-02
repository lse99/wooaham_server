package Wooaham.wooaham_server.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_location")
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "longitude", nullable = false)
    private Double lng;

    @Column(name = "latitude", nullable = false)
    private Double lat;

    public Location(Long userId, Double lng, Double lat) {
        this.userId = userId;
        this.lng = lng;
        this.lat = lat;
    }
}
