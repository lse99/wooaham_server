package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PhoneTimeAlarmRepository extends JpaRepository<PhoneTimeAlarm, Long> {

    @Query("select p from PhoneTimeAlarm p where p.studentId = :userId")
    Optional<PhoneTimeAlarm> findByStudentId(@Param("userId") Long userId);
}
