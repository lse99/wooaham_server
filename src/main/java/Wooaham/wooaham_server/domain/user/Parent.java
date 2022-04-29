package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Parent {
    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private Long primaryStudentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "parent")
    private List<Student> children = new ArrayList<>();

    public Parent(User user){
        this.user = user;
    }
}
