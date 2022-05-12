package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.Alarm;
import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.request.AlarmRequest;
import Wooaham.wooaham_server.dto.response.AlarmResponse;
import Wooaham.wooaham_server.repository.AlarmRepository;
import Wooaham.wooaham_server.repository.IconRepository;
import Wooaham.wooaham_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final IconRepository iconRepository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public List<AlarmResponse> findAlarms(){
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        return alarmRepository.findAllByUserId(userInfo.getUserId());
    }

    @Transactional(readOnly = true)
    public AlarmResponse findOne(Long alarmId){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        return AlarmResponse.of(findAlarm);
    }

    public Long addAlarm(AlarmRequest req){
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        User user = userRepository.findById(userInfo.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
        Icon icon = iconRepository.findById(req.getIconId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ICON));
        Alarm alarm = req.toAlarm(user, icon);
        alarmRepository.save(alarm);
        return alarm.getId();
    }

    public AlarmResponse updateAlarm(Long alarmId, AlarmRequest req){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        Icon icon = iconRepository.findById(req.getIconId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ICON));
        findAlarm.updateAlarm(req.getTitle(), req.getTime(),
                req.getDaysOfWeek(), req.getBefore10min(), icon);
        alarmRepository.save(findAlarm);
        return AlarmResponse.of(findAlarm);
    }

    public Boolean turnAlarmOnOff(Long alarmId, AlarmRequest req){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        findAlarm.turnAlarmOnOff(req.getEnabled());
        alarmRepository.save(findAlarm);
        return findAlarm.getEnabled();
    }

    public void deleteAlarm(Long alarmId){
        Alarm findAlarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        alarmRepository.delete(findAlarm);
    }

}
