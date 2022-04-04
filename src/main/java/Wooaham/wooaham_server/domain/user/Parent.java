package Wooaham.wooaham_server.domain.user;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Parent extends User{
    @OneToMany(mappedBy = "parent")
    private List<Student> children = new ArrayList<>();
}
