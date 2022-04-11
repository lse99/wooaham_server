package Wooaham.wooaham_server.domain.notice;

import Wooaham.wooaham_server.domain.user.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String contents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Teacher user;

    @OneToMany(mappedBy = "notice")
    private List<Reader> readers = new ArrayList<>();


    private Notice(Teacher user, String title, String contents){
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public static Notice createNotice(Teacher user, String title, String contents){
        Notice notice = new Notice(user, title, contents);
        return notice;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContents(String contents){
        this.contents = contents;
    }

}
