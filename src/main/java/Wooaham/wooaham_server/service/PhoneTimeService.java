package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.PhoneTimeAlarm;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.request.PhoneTimeAlarmRequest;
import Wooaham.wooaham_server.dto.response.PhoneTimeAlarmResponse;
import Wooaham.wooaham_server.repository.ParentRepository;
import Wooaham.wooaham_server.repository.PhoneTimeAlarmRepository;
import Wooaham.wooaham_server.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PhoneTimeService {
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final PhoneTimeAlarmRepository phoneTimeAlarmRepository;
    private final JwtService jwtService;

    public void findPhoneUsageTime(){

    }

    public void updatePhoneUsageTime(){
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        Parent parent = parentRepository.findByUserId(userInfo.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));
        Student student = studentRepository.findByUserId(parent.getPrimaryStudentId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

    }

    public PhoneTimeAlarmResponse findPhoneTimeAlarm(){
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        Parent parent = parentRepository.findByUserId(userInfo.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));
        PhoneTimeAlarm phoneTimeAlarm = phoneTimeAlarmRepository.findByStudentId(parent.getPrimaryStudentId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ALARM));
        return PhoneTimeAlarmResponse.of(phoneTimeAlarm);
    }

    public Long addPhoneTimeAlarm(PhoneTimeAlarmRequest req){
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        Parent parent = parentRepository.findByUserId(userInfo.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));
        PhoneTimeAlarm phoneTimeAlarm;
        if(phoneTimeAlarmRepository.findByStudentId(parent.getPrimaryStudentId()).isEmpty()) {
            phoneTimeAlarm = req.toPhoneTimeAlarm(parent);
            phoneTimeAlarmRepository.save(phoneTimeAlarm);
        }
        else {
            phoneTimeAlarm = phoneTimeAlarmRepository.findByStudentId(parent.getPrimaryStudentId()).get();
            phoneTimeAlarm.updatePhoneTimeAlarm(req.getHour(), req.getMinute());
            phoneTimeAlarmRepository.save(phoneTimeAlarm);
        }
        return phoneTimeAlarm.getId();
    }

    public void deletePhoneTimeAlarm(Long alarmId){

    }

}
