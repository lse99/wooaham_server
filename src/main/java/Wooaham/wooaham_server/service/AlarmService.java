package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.dto.request.AlarmRequest;
import Wooaham.wooaham_server.repository.AlarmRepository;
import Wooaham.wooaham_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Alarm> findAlarms(Long userId){
        return alarmRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Alarm findOne(Long alarmId){
        return alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
    }

    public Long addAlarm(Long userId, AlarmRequest req){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
        Alarm alarm = req.toAlarm(user);
        alarmRepository.save(alarm);
        return alarm.getId();
    }

    public Alarm updateAlarm(Long alarmId, AlarmRequest req){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        findAlarm.updateAlarm(req.getTitle(), req.getHour(), req.getMinute(), req.getDaysOfWeek(),
                req.getBefore10min());
        alarmRepository.save(findAlarm);
        return findAlarm;
    }

    public void turnAlarmOnOff(Long alarmId){

    }

    public void deleteAlarm(Long alarmId){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        alarmRepository.delete(findAlarm);
    }

}
