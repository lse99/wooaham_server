package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.Homework;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeworkResponse {
    private Long id;
    private Long userId;
    private String title;
    private Boolean checked;

    public static HomeworkResponse of(Homework homework){
        return new HomeworkResponse(homework.getId(), homework.getUser().getUser().getId(),
                homework.getTitle(), homework.getChecked());
    }
}
