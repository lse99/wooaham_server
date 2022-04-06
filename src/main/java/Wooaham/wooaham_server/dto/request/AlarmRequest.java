package Wooaham.wooaham_server.dto.request;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmRequest {
    private String title;
    private Integer hour;
    private Integer minute;
    private Integer daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    public Alarm toAlarm(User user) {
        return Alarm.createAlarm(user, this.title, this.hour, this.minute, this.daysOfWeek,
                this.enabled, this.before10min);
    }
}
