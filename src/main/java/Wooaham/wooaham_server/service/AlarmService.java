package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public List<Alarm> findAlarms(Long userId){
        return alarmRepository.findByUserId(userId);
    }

    public Alarm findOne(Long alarmId){
        return alarmRepository.findOne(alarmId);
    }
}
