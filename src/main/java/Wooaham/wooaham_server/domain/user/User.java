package Wooaham.wooaham_server.domain.user;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.Icon;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "icon_id")
    private Long iconId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", insertable = false, updatable = false)
    private Icon icon;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birth", nullable = false)
    private String birth;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Alarm> alarms = new ArrayList<>();

}

