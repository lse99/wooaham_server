package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.SchoolInfo;
import Wooaham.wooaham_server.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    public List<SchoolInfo> getSchoolInfo(String schoolName) {
        return schoolRepository.findByName(schoolName);
    }
}
