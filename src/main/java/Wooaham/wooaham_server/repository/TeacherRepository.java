package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("select t from Teacher t where t.user.id = :userId")
    Optional<Teacher> findByUserId(@Param("userId") Long userId);
}
