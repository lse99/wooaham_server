package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.Student;
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

    public void findPhoneUsageTime(){

    }

    public void updatePhoneUsageTime(Long userId){
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

    }

    public void addPhomeTimeAlarm(){

    }

    public void updatePhoneTimeAlarm(){

    }

    public void deletePhoneTimeAlarm(Long alarmId){

    }

}
