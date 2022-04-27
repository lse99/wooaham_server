package Wooaham.wooaham_server.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;

    private String officeCode; // ATPT_OFCDC_SC_CODE
    private String schoolName; // SCHUL_NM
    private String schoolCode; // SD_SCHUL_CODE
    private String classCode;
    private Integer grade;
    private Integer classNum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Teacher(User user) {
        this.user = user;
    }

    public void setSchoolInfo(String officeCode, String schoolName, String schoolCode) {
        this.officeCode = officeCode;
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
    }
}
