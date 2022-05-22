package Wooaham.wooaham_server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "school")
public class SchoolInfo {
    @Id
    @GeneratedValue
    @Column(name = "SD_SCHUL_CODE")
    private String schoolCode;

    @Column(name = "SCHUL_NM")
    private String schoolName;

    @Column(name = "ATPT_OFCDC_SC_CODE")
    private String officeCode;

    @Column(name = "LCTN_SC_NM")
    private String region;
}
