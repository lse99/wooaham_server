package Wooaham.wooaham_server.dto.request;

import Wooaham.wooaham_server.domain.notice.Notice;
import Wooaham.wooaham_server.domain.user.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticeRequest {
    private String title;
    private String contents;

    public Notice toNotice(Teacher user){
        return Notice.createNotice(user, this.title, this.contents);
    }
}
