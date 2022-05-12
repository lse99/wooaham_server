package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneTimeAlarmResponse {
    private Integer hour;
    private Integer minute;

    public static PhoneTimeAlarmResponse of(PhoneTimeAlarm alarm){
        return new PhoneTimeAlarmResponse(alarm.getHour(), alarm.getMinute());
    }
}
