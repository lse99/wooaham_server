package Wooaham.wooaham_server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    private String sidoCode;
    private String schoolCode;
    private String grade;
    private String class_nm;

    private String startDay;
    private String endDay;

}
