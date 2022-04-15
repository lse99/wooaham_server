package Wooaham.wooaham_server.dto.request;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.repository.IconRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmRequest {
    private String title;
    private String time;
    private String daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    private Long iconId;

    public Alarm toAlarm(User user, Icon icon) {
        return Alarm.createAlarm(user, icon, this.title, this.time,
                this.daysOfWeek, this.enabled, this.before10min);
    }
}
