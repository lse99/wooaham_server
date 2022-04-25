package Wooaham.wooaham_server.domain.user;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.type.UserType;
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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "birth")
    private String birth;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Alarm> alarms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserType role;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Parent> parents = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Teacher> teachers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Student> students = new ArrayList<>();

    public Boolean isActivated(){
        return this.deletedAt == null;
    }

}

