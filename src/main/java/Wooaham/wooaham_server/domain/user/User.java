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
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "icon_id")
    private Long iconId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icon_id", insertable = false, updatable = false)
    private Icon icon;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private String birth;
    @Enumerated(EnumType.STRING)
    private UserType role;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Alarm> alarms = new ArrayList<>();

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

    public User(String email, String password, String name, String birth, UserType role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.role = role;
        this.iconId = null;
        this.deletedAt = null;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}

