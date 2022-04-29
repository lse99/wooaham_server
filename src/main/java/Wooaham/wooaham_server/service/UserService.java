package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.*;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .filter(User::isActivated)
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
    }

    @Transactional(readOnly = true)
    public List<UserDto.Child> getChildren(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Parent parent = parentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        List<Student> result = studentRepository.findAllByParentId(parent.getId());

        if (result.isEmpty()) throw new BaseException(ErrorCode.NOTFOUND_CHILDREN);
        return result.stream()
                .map(UserDto.Child::from)
                .collect(Collectors.toList());
    }

    public void registerName(Long userId, UserDto.RegisterName userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        //TODO 닉네임 정규표현식 검사 로직 추가

        user.setName(userDto.getName());
        userRepository.save(user);
    }

    public void registerSchool(Long userId, UserDto.RegisterSchool userDto) {
        User user = userRepository.findById(userId).orElseThrow();

        switch (user.getRole()) {
            case TEACHER:
                Teacher teacher = teacherRepository.findByUserId(userId).orElseThrow();
                teacher.setSchoolInfo(
                        userDto.getOfficeCode(),
                        userDto.getSchoolName(),
                        userDto.getSchoolCode()
                );
                teacherRepository.save(teacher);
                break;
            case STUDENT:
                Student student = studentRepository.findByUserId(userId).orElseThrow();
                student.setSchoolInfo(
                        userDto.getOfficeCode(),
                        userDto.getSchoolName(),
                        userDto.getSchoolCode());
                studentRepository.save(student);
                break;
            default:
                throw new RuntimeException();
        }
    }

    public void registerClass(Long userId, UserDto.RegisterClass userDto) {
        User user = userRepository.findById(userId).orElseThrow();

        switch (user.getRole()) {
            case TEACHER:
                Teacher teacher = teacherRepository.findByUserId(userId).orElseThrow();
                teacher.setClassInfo(
                        userDto.getGrade(),
                        userDto.getClassNum()
                );
                teacherRepository.save(teacher);
                break;
            case STUDENT:
                Student student = studentRepository.findByUserId(userId).orElseThrow();
                student.setClassInfo(
                        userDto.getGrade(),
                        userDto.getClassNum());
                studentRepository.save(student);
                break;
            default:
                throw new RuntimeException();
        }
    }

    public void registerRole(Long userId, UserDto.RegisterRole userDto) {
        UserType role = userDto.getRole();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        if (user.getRole() != null) throw new BaseException(ErrorCode.CONFLICT_USER_ROLE);

        user.setRole(role);

        switch (role) {
            case PARENT:
                Parent parent = new Parent(user);
                parentRepository.save(parent);
                break;
            case TEACHER:
                Teacher teacher = new Teacher(user);
                teacherRepository.save(teacher);
                break;
            case STUDENT:
                Student student = new Student(user);
                studentRepository.save(student);
                break;
            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_TYPE);
        }
    }

    public void link(Long userId, UserDto.Link userDto) {
        Student student = studentRepository.findByUserId(userId).orElseThrow();
        Parent parent = parentRepository.findById(userDto.getParentId()).orElseThrow();

        if (parent.getPrimaryStudentId() == null) parent.setPrimaryStudentId(student.getId());

        student.setParent(parent);
        studentRepository.save(student);
    }

    public void changeLink(Long userId, UserDto.ChangeLink userDto) {
        Parent parent = parentRepository.findByUserId(userId).orElseThrow();
        parent.setPrimaryStudentId(userDto.getStudentId());
        parentRepository.save(parent);
    }

    public void registerIcon(Long userId, UserDto.RegisterIcon userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Icon icon = iconRepository.findById(userDto.getIconId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_ICON));

        user.setIconId(icon.getIconId());
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        if (user.getDeletedAt() != null) throw new BaseException(ErrorCode.CONFLICT_USER_DELETED);

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
