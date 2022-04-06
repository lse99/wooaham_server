package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Alarm {
    @Id @GeneratedValue
    @Column(name = "alarm_id")
    private Long id;

    private String title;
    private Integer hour;
    private Integer minute;
    private Integer daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICON_ID")
    private Icon icon;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private Alarm(User user, String title, Integer hour, Integer minute,
                 Integer daysOfWeek, Boolean enabled, Boolean before10min) {
        this.user = user;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.daysOfWeek = daysOfWeek;
        this.enabled = enabled;
        this.before10min = before10min;
        user.getAlarms().add(this);
    }

    public static Alarm createAlarm(User user, String title, Integer hour, Integer minute,
                                    Integer daysOfWeek, Boolean enabled, Boolean before10min){
        Alarm alarm = new Alarm(user, title, hour, minute, daysOfWeek, enabled, before10min);
        return alarm;
    }

    public void updateAlarm(String title, Integer hour, Integer minute,
                            Integer daysOfWeek, Boolean before10min) {
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.daysOfWeek = daysOfWeek;
        this.before10min = before10min;
    }

    public void turnAlarmOnOff(boolean enabled){
        this.enabled = enabled;
    }
}
