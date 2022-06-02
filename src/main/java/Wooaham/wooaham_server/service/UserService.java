package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.Location;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.*;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.UserDto.*;
import Wooaham.wooaham_server.repository.*;
import Wooaham.wooaham_server.utils.AES128;
import Wooaham.wooaham_server.utils.Secret;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    // TODO QueryDsl 설정 추가, 탈퇴하지 않은 사용자들만 조회하는 함수 정의해서 사용하기.
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final IconRepository iconRepository;
    private final LocationRepository locationRepository;
    private final JwtService jwtService;
    private final MapService mapService;

    public boolean checkEmail(String email) {
        String emailExp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        if (email == null) throw new BaseException(ErrorCode.EMPTY_EMAIL);
        else if (!Pattern.matches(emailExp, email)) throw new BaseException(ErrorCode.INVALID_EMAIL);
        else if (userRepository.findByEmail(email).isPresent()) throw new BaseException(ErrorCode.CONFLICT_USER);
        else return true;
    }

    public boolean checkLogInEmail(String email) {
        String emailExp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";

        if (email == null) throw new BaseException(ErrorCode.EMPTY_EMAIL);
        else if (!Pattern.matches(emailExp, email)) throw new BaseException(ErrorCode.INVALID_EMAIL);
        else return true;
    }

    public boolean checkPw(String pw) {
        String pwExp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}";

        if (pw == null) throw new BaseException(ErrorCode.EMPTY_PASSWORD);
        else if (!Pattern.matches(pwExp, pw)) throw new BaseException(ErrorCode.INVALID_PASSWORD);
        else if (pw.length() < 8 || pw.length() > 20) throw new BaseException(ErrorCode.INVALID_PASSWORD_SIZE);
        else return true;
    }

    public boolean checkName(String name) {
        if (name == null) throw new BaseException(ErrorCode.EMPTY_NAME);
        return true;
    }

    public boolean checkBirth(String birth) {
        String birthExp = "^\\d{8}$";
        if (birth == null) throw new BaseException(ErrorCode.EMPTY_BIRTH);
        else if (!Pattern.matches(birthExp, birth)) throw new BaseException(ErrorCode.INVALID_BIRTH);
        return true;
    }

    public boolean checkRole(UserType role) {
        if (role == null) throw new BaseException(ErrorCode.EMPTY_ROLE);
        return true;
    }

    @Transactional
    public void registerRole(User user) {
        UserType role = user.getRole();

        switch (role) {
            case PARENT:
                user.setIconId(1L);
                userRepository.save(user);

                Parent parent = new Parent(user);
                parentRepository.save(parent);
                break;
            case TEACHER:
                user.setIconId(2L);
                userRepository.save(user);

                Teacher teacher = new Teacher(user);
                teacherRepository.save(teacher);
                break;
            case STUDENT:
                user.setIconId(3L);
                userRepository.save(user);

                Student student = new Student(user);
                studentRepository.save(student);
                break;
            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_TYPE);
        }
    }

    @Transactional
    public Long registerUser(UserDto.Create userDto) {

        if (checkEmail(userDto.getEmail()) && checkPw(userDto.getPassword()) && checkName(userDto.getName()) && checkBirth(userDto.getBirth())) {
            checkRole(userDto.getRole());
        }

        try {
            String password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(userDto.getPassword());
            userDto.setPassword(password);

            User user = new User(
                    userDto.getEmail(), password, userDto.getName(), userDto.getBirth(), userDto.getRole()
            );

            userRepository.save(user);
            registerRole(user);

            return user.getId();

        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_ENCRYPTION_ERROR);
        }
    }

    @Transactional
    public LogInRes logIn(UserDto.LogInReq userDto) {

        if (checkLogInEmail(userDto.getEmail())) {
            checkPw(userDto.getPassword());
        }

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        user = userRepository.findActiveUser(user.getId());

        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_DECRYPTION_ERROR);
        }

        if (password.equals(userDto.getPassword())) {
            Long userId = user.getId();

            UserType role = user.getRole();
            String jwt = jwtService.createJwt(userId, role);

            return new LogInRes(userId, role, jwt);

        } else {
            throw new BaseException(ErrorCode.FAILED_TO_LOGIN);
        }
    }

    @Transactional
    public UserDto getUser(Long userId) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        if (userInfoByJwt.getRole() == UserType.STUDENT) {
            mapService.createLocation(userId);
        }

        return userRepository.findById(userId)
                .filter(User::isActivated)
                .map(UserDto::from)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
    }

    @Transactional(readOnly = true)
    public List<UserDto.Child> getChildren(Long userId) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);


        User user = userRepository.findActiveUser(userId);

        Parent parent = parentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        List<Student> result = studentRepository.findAllByParentId(parent.getId());

        if (result.isEmpty()) throw new BaseException(ErrorCode.NOTFOUND_CHILDREN);

        return result.stream()
                .map(UserDto.Child::from)
                .collect(Collectors.toList());
    }

    public Long changePw(Long userId, UserDto.ChangePw userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        if (userInfoByJwt.getRole() == UserType.STUDENT) {
            mapService.createLocation(userId);
        }

        User user = userRepository.findActiveUser(userId);

        if (checkPw(userDto.getCurrentPw())) {
            checkPw(userDto.getNewPw());
        }

        String currentPw;
        String newPw;
        try {
            currentPw = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());

            if (currentPw.equals(userDto.getCurrentPw())) {
                newPw = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(userDto.getNewPw());
                user.setPassword(newPw);
                userRepository.save(user);
            }
            return user.getId();

        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_CHANGE_ERROR);
        }
    }

    public Long changeName(Long userId, UserDto.changeName userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        if (userInfoByJwt.getRole() == UserType.STUDENT) {
            mapService.createLocation(userId);
        }

        User user = userRepository.findActiveUser(userId);

        user.setName(userDto.getName());
        userRepository.save(user);

        return user.getId();
    }

    public Long registerSchool(Long userId, UserDto.RegisterSchool userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user = userRepository.findActiveUser(userId);

        switch (user.getRole()) {
            case TEACHER:
                Teacher teacher = teacherRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_TEACHER));

                teacher.setSchoolInfo(
                        userDto.getOfficeCode(),
                        userDto.getSchoolName(),
                        userDto.getSchoolCode()
                );
                teacherRepository.save(teacher);

                return user.getId();

            case STUDENT:
                Student student = studentRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

                student.setSchoolInfo(
                        userDto.getOfficeCode(),
                        userDto.getSchoolName(),
                        userDto.getSchoolCode());
                studentRepository.save(student);

                return user.getId();

            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_FOR_SCHOOL);
        }
    }

    public Long registerClass(Long userId, UserDto.RegisterClass userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user = userRepository.findActiveUser(userId);

        switch (user.getRole()) {
            case TEACHER:
                Teacher teacher = teacherRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_TEACHER));

                if (teacher.getSchoolCode() == null) throw new BaseException(ErrorCode.NOT_FOUND_SCHOOL);

                teacher.setClassInfo(
                        userDto.getGrade(),
                        userDto.getClassNum()
                );
                teacherRepository.save(teacher);

                return user.getId();

            case STUDENT:
                Student student = studentRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

                if (student.getSchoolCode() == null) throw new BaseException(ErrorCode.NOT_FOUND_SCHOOL);

                student.setClassInfo(
                        userDto.getGrade(),
                        userDto.getClassNum());
                studentRepository.save(student);

                return user.getId();

            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_FOR_CLASS);
        }
    }

    public Long registerLink(Long userId, UserDto.RegisterLink userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user_student = userRepository.findActiveUser(userId);

        Student student = studentRepository.findByUserId(user_student.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

        if (student.getParentId() != null) throw new BaseException(ErrorCode.CONFLICT_LINK);

        User user_parent = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Parent parent = parentRepository.findByUserId(user_parent.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        if (parent.getPrimaryStudentId() == null) parent.setPrimaryStudentId(student.getUserId());

        student.setParentId(parent.getId());
        studentRepository.save(student);

        return user_student.getId();
    }

    public Long changeLink(Long userId, UserDto.ChangeLink userDto) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user = userRepository.findActiveUser(userId);

        Parent parent = parentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        Student student = studentRepository.findById(userDto.getStudentId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

        parent.setPrimaryStudentId(student.getUserId());
        parentRepository.save(parent);

        return user.getId();

    }

    public void registerIcon(Long userId, UserDto.RegisterIcon userDto) {

        User user = userRepository.findActiveUser(userId);

        Icon icon = iconRepository.findById(userDto.getIconId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ICON));

        user.setIconId(icon.getIconId());
        userRepository.save(user);
    }

    public Long deleteUser(Long userId) {

        UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), userId))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user = userRepository.findActiveUser(userId);

        if (user.getDeletedAt() != null) throw new BaseException(ErrorCode.CONFLICT_USER_DELETED);

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return user.getId();
    }
}
