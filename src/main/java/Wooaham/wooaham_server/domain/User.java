package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.type.RoleType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "icon_id")
    private Long iconId;

    @ManyToOne
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
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

}

