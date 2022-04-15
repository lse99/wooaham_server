package Wooaham.wooaham_server.domain.user;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Teacher {
    @Id @GeneratedValue
    private Long id;

    private String classCode;
    private String schoolName;
    private Integer grade;
    private Integer classNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

}
