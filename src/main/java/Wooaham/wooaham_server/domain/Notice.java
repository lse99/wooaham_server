package Wooaham.wooaham_server.domain;

import Wooaham.wooaham_server.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String title;
    private String contents;
    private String classCode;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
