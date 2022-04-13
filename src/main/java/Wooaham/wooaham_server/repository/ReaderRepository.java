package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.notice.Notice;
import Wooaham.wooaham_server.domain.notice.Reader;
import Wooaham.wooaham_server.domain.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Reader findByNoticeAndParent(Notice notice, Parent parent);
}
