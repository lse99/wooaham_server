package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUserId(Long userId);

    Student findByClassCodeAndParent(String classCode, Parent parent);

}
