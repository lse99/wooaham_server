package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.SchoolInfo;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final JwtService jwtService;
    private final MapService mapService;
    public List<SchoolInfo> getSchoolInfo(String schoolName) {

        UserDto.UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (userInfoByJwt.getRole() == UserType.STUDENT) {
            mapService.createLocation(userInfoByJwt.getUserId());
        }
        return schoolRepository.findByName(schoolName);
    }
}
