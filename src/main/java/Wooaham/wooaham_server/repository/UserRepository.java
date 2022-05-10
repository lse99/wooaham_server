package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.QUser;
import Wooaham.wooaham_server.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    QUser user = QUser.user;
    default User findActiveUser(Long userId){
        return findOne(
                user.id.eq(userId).and(user.deletedAt.isNull())
        ).orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
    }
    Optional<User> findByEmail(String email);
}
