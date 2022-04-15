package Wooaham.wooaham_server.domain.user;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Student {
    @Id @GeneratedValue
    private Long id;

    private String classCode;
    private String schoolName;
    private Integer grade;
    private Integer classNum;
    private Integer phoneUsageTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    public Student(User user){
        this.user = user;
    }
}
