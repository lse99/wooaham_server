package Wooaham.wooaham_server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class School {
    @Id
    @GeneratedValue
    @Column(name = " SD_SCHUL_CODE")
    private String schoolCode;

    @Column(name = "SCHUL_NM")
    private String schoolName;

    @Column(name = "ATPT_OFCDC_SC_CODE")
    private String officeCode;
}
