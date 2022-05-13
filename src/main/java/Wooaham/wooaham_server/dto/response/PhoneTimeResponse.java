package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhoneTimeResponse {
    private Integer hour;
    private Integer minute;

    public static PhoneTimeResponse of(PhoneTimeAlarm alarm){
        return new PhoneTimeResponse(alarm.getHour(), alarm.getMinute());
    }

    public static PhoneTimeResponse of(Integer hour, Integer minute) {
        return new PhoneTimeResponse(hour, minute);
    }
}
