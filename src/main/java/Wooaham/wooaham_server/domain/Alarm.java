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
    private String ampm;
    private String daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICON_ID")
    private Icon icon;

    //TODO created_at, updated_at 처리 (notice)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Alarm(User user, Icon icon, String title, Integer hour, Integer minute,
                 String ampm, String daysOfWeek, Boolean enabled, Boolean before10min) {
        this.user = user;
        this.icon = icon;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.ampm = ampm;
        this.daysOfWeek = daysOfWeek;
        this.enabled = enabled;
        this.before10min = before10min;
        user.getAlarms().add(this);
    }

    public static Alarm createAlarm(User user, Icon icon, String title, Integer hour, Integer minute,
                                    String ampm, String daysOfWeek, Boolean enabled, Boolean before10min){
        Alarm alarm = new Alarm(user, icon, title, hour, minute, ampm, daysOfWeek, enabled, before10min);
        return alarm;
    }

    public void updateAlarm(String title, Integer hour, Integer minute, String ampm,
                            String daysOfWeek, Boolean before10min, Icon icon) {
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.ampm = ampm;
        this.daysOfWeek = daysOfWeek;
        this.before10min = before10min;
        this.icon = icon;
    }

    public void turnAlarmOnOff(boolean enabled){
        this.enabled = enabled;
    }
}
