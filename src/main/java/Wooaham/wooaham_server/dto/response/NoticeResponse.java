package Wooaham.wooaham_server.dto.response;

import Wooaham.wooaham_server.domain.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeResponse {
    private Long noticeId;
    private Long userId;
    private String title;
    private String contents;

    public static NoticeResponse of(Notice notice){
        return new NoticeResponse(notice.getId(), notice.getUser().getUser().getId(), notice.getTitle(), notice.getContents());
    }
}
