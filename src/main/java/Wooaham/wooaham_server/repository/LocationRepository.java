package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Location;
import Wooaham.wooaham_server.domain.QLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {
    ;
    QLocation location = QLocation.location;

    default List<Location> getRecentStudentLocation(Long userId) {
        return (List<Location>) findAll(
                location.userId.eq(userId), Sort.by(Sort.Direction.DESC, "id")
        );
    }


}
