package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Homework;
import Wooaham.wooaham_server.domain.type.HomeworkType;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.dto.response.HomeworkResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    //@Query("select h from Homework h where h.user.id = :classCode")
    List<HomeworkResponse> findByUserAndType(Student user, HomeworkType type);

}
