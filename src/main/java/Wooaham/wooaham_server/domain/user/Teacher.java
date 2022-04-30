package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String officeCode; // ATPT_OFCDC_SC_CODE
    private String schoolName; // SCHUL_NM
    private String schoolCode; // SD_SCHUL_CODE
    private String classCode;
    private Integer grade;
    private Integer classNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    public Teacher(User user) {
        this.userId = user.getId();
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setSchoolInfo(String officeCode, String schoolName, String schoolCode) {
        this.officeCode = officeCode;
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
    }

    public void setClassInfo(Integer grade, Integer classNum){
        this.grade = grade;
        this.classNum = classNum;
        this.classCode = this.schoolCode + "_" + this.grade + "_" + this.classNum;
    }
}
