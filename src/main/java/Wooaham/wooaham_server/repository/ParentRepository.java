package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    @Query("select p from Parent p where p.user.id = :userId")
    Optional<Parent> findByUserId(@Param("userId") Long userId);
}
