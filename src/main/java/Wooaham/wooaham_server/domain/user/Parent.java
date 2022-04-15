package Wooaham.wooaham_server.domain.user;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Parent {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "parent")
    private List<Student> children = new ArrayList<>();

    public Parent(User user){
        this.user = user;
    }
}
