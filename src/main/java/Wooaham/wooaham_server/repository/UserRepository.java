package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface UserRepository extends JpaRepository<User, Long>{
}
