package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.dto.response.AlarmResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    //TODO 쿼리 없애고 user 객체로 찾기
    @Query("select a from Alarm a where a.user.id = :userId")
    List<AlarmResponse> findAllByUserId(@Param("userId") Long userId);
}
