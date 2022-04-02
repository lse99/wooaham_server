package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Icon;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IconRepository extends JpaRepository<Icon, Long> {

    @NotNull List<Icon> findAll();

    @NotNull Optional<Icon> findById(@NotNull Long id);

}
