package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneTimeAlarmRepository extends JpaRepository<PhoneTimeAlarm, Long> {

    @Query("select p from PhoneTimeAlarm p where p.parent.user.id = :userId")
    PhoneTimeAlarm findByUserId(@Param("userId") Long userId);
}
