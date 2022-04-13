package Wooaham.wooaham_server.domain.notice;

import Wooaham.wooaham_server.domain.user.Parent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reader {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTICE_ID")
    private Notice notice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    public Reader(Notice notice, Parent parent){
        this.notice = notice;
        this.parent = parent;
        notice.getReaders().add(this);
    }

    public static Reader of(Notice notice, Parent parent){
        return new Reader(notice, parent);
    }

}
