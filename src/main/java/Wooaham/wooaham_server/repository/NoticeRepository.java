package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
