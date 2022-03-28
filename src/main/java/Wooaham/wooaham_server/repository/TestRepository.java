package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<User, Long> {
    List<User> findAll();
}
