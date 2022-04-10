package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select n from Notice n where n.user.classCode = :classCode")
    List<Notice> findAllByClassCode(@Param("classCode") String classCode);
}
