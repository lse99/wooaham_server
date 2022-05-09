package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.user.Parent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PhoneTimeAlarm {
    @Id @GeneratedValue
    @Column(name = "phone_alarm_id")
    private Long id;

    private Integer hour;
    private Integer minute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private Long studentId;

    private PhoneTimeAlarm(Parent parent, Integer hour, Integer minute){
        this.parent = parent;
        this.studentId = parent.getPrimaryStudentId();
        this.hour = hour;
        this.minute = minute;
    }

    public static PhoneTimeAlarm createPhoneTimeAlarm(Parent parent, Integer hour, Integer minute){
        return new PhoneTimeAlarm(parent, hour, minute);
    }
}
