package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.StoreInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface StoreRepository extends JpaRepository<StoreInfo, String>, QuerydslPredicateExecutor<StoreInfo> {
    //QStoreInfo storeInfo = QStoreInfo.storeInfo;
}
