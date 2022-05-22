package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.QSchoolInfo;
import Wooaham.wooaham_server.domain.SchoolInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolInfo, String>, QuerydslPredicateExecutor<SchoolInfo> {
    QSchoolInfo schoolInfo = QSchoolInfo.schoolInfo;
    default List<SchoolInfo> findByName(String name) {
        return (List<SchoolInfo>) findAll(schoolInfo.schoolName.eq(name));
    }
}
