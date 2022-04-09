package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class Teacher extends User{
    private String classCode;
    private String schoolName;
    private int grade;
    private int class_num;
}
