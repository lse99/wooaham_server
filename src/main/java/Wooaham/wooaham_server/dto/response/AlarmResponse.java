package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long alarmId;
    private Long userId;
    private Long iconId;
    private String title;
    private String time;
    private String daysOfWeek;
    private Boolean enabled;
    private Boolean before10min;

    public static AlarmResponse of(Alarm alarm){
        return new AlarmResponse(alarm.getId(), alarm.getUser().getId(), alarm.getIcon().getIconId(), alarm.getTitle(),
                alarm.getTime(),alarm.getDaysOfWeek(), alarm.getEnabled(), alarm.getBefore10min());
    }
}
