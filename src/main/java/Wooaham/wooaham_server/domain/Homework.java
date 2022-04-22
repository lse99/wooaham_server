package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.type.HomeworkType;
import Wooaham.wooaham_server.domain.user.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue
    @Column(name = "homework_id")
    private Long id;

    private String title;
    private Boolean checked;

    @Enumerated(EnumType.STRING)
    private HomeworkType type;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Student user;

    private Homework(Student user, String title, Boolean checked){
        this.user = user;
        this.title = title;
        this.checked = checked;
    }

    public static Homework createSchoolHomework(Student user, String title, Boolean checked){
        Homework homework = new Homework(user, title, checked);
        homework.type = HomeworkType.SCHOOL;
        return homework;
    }
    public static Homework createAcademyHomework(Student user, String title, Boolean checked){
        Homework homework = new Homework(user, title, checked);
        homework.type = HomeworkType.ACADEMY;
        return homework;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateCheck(){
        if(checked) checked = false;
        else checked = true;
    }
}
