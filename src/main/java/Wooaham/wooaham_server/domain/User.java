package Wooaham.wooaham_server.domain;

import lombok.*;
import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}

