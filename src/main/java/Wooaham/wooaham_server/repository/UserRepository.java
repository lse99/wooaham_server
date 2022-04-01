package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull List<User> findAll();

    @NotNull Optional<User> findById(@NotNull Long id)    ;
}
