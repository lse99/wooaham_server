package Wooaham.wooaham_server.repository;

import Wooaham.wooaham_server.domain.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlarmRepository {

    private final EntityManager em;

    public void save(Alarm alarm){
        em.persist(alarm);
    }

    public Alarm findOne(Long id){
        return em.find(Alarm.class, id);
    }

    public List<Alarm> findByUserId(Long userId){
        return em.createQuery("select a from Alarm a where a.user.id = :userId", Alarm.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
