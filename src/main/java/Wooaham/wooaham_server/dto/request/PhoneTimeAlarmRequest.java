package Wooaham.wooaham_server.dto.request;

import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import Wooaham.wooaham_server.domain.user.Parent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneTimeAlarmRequest {
    private Integer hour;
    private Integer minute;

    public PhoneTimeAlarm toPhoneTimeAlarm(Parent parent){
        return PhoneTimeAlarm.createPhoneTimeAlarm(parent, this.hour, this.minute);
    }
}
