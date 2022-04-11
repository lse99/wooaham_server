package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
