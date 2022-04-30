package Wooaham.wooaham_server.domain.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id @GeneratedValue
    private Long id;
    private Long userId;
    private Long parentId;
    private String officeCode; // ATPT_OFCDC_SC_CODE
    private String schoolName; // SCHUL_NM
    private String schoolCode; // SD_SCHUL_CODE
    private String classCode;
    private Integer grade;
    private Integer classNum;
    private Integer phoneUsageTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private Parent parent;

    public Student(User user){
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
