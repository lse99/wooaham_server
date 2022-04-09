package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
public class Student extends User{
    private String classCode;
    private String schoolName;
    private Integer grade;
    private Integer class_num;
    private Integer phoneUsageTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
}
