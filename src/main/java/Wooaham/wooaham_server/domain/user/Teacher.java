package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class Teacher extends User{
    private String classCode;
    private String schoolName;
    private Integer grade;
    private Integer class_num;
}
