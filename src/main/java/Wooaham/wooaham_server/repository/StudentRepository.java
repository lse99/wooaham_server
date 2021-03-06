package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("select s from Student s where s.user.id = :userId")
    Optional<Student> findByUserId(@Param("userId")Long userId);

    List<Student> findAllByParentId(Long parentId);

    Student findByClassCodeAndParent(String classCode, Parent parent);

}
