package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.notice.Notice;
import Wooaham.wooaham_server.dto.response.NoticeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n where n.user.classCode = :classCode")
    List<NoticeResponse> findAllByClassCode(@Param("classCode") String classCode);
}
