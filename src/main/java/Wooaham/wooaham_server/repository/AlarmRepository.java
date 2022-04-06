package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findAllByUserId(Long userId);
}
