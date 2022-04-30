package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Icon;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.*;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.*;
import Wooaham.wooaham_server.utils.AES128;
import Wooaham.wooaham_server.utils.Secret;
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

    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
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
    public void registerUser(UserDto.Create userDto) {
        if (checkEmail(userDto.getEmail())) throw new BaseException(ErrorCode.CONFLICT_USER);

        try {
            String password = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(userDto.getPassword());
            userDto.setPassword(password);

            User user = new User(
                            userDto.getEmail(), password, userDto.getName(), userDto.getBirth(), userDto.getRole()
                        );

            userRepository.save(user);

            registerRole(user);

        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_ENCRYPTION_ERROR);
        }
    }

    @Transactional
    public Long logIn(UserDto.LogIn userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_DECRYPTION_ERROR);
        }

        if (password.equals(userDto.getPassword())) {
            return user.getId();
        } else {
            throw new BaseException(ErrorCode.FAILED_TO_LOGIN);
        }
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

    public void changePw(Long userId, UserDto.ChangePw userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        String currentPw;
        String newPw;
        try {
            currentPw = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());

            if (currentPw.equals(userDto.getCurrentPw())) {
                newPw = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(userDto.getNewPw());
                user.setPassword(newPw);
                userRepository.save(user);
            }
        } catch (Exception ignored) {
            throw new BaseException(ErrorCode.PASSWORD_CHANGE_ERROR);
        }
    }

    public void changeName(Long userId, UserDto.changeName userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        user.setName(userDto.getName());
        userRepository.save(user);
    }

    public void registerSchool(Long userId, UserDto.RegisterSchool userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

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

                break;

            case STUDENT:
                Student student = studentRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

                student.setSchoolInfo(
                        userDto.getOfficeCode(),
                        userDto.getSchoolName(),
                        userDto.getSchoolCode());
                studentRepository.save(student);

                break;

            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_FOR_SCHOOL);
        }
    }

    public void registerClass(Long userId, UserDto.RegisterClass userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

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

                break;

            case STUDENT:
                Student student = studentRepository.findByUserId(userId)
                        .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

                if (student.getSchoolCode() == null) throw new BaseException(ErrorCode.NOT_FOUND_SCHOOL);

                student.setClassInfo(
                        userDto.getGrade(),
                        userDto.getClassNum());
                studentRepository.save(student);

                break;

            default:
                throw new BaseException(ErrorCode.INVALID_ROLE_FOR_CLASS);
        }
    }

    public void registerLink(Long userId, UserDto.RegisterLink userDto) {

        User user_student = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Student student = studentRepository.findByUserId(user_student.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

        if (student.getParentId() != null) throw new BaseException(ErrorCode.CONFLICT_LINK);

        User user_parent = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Parent parent = parentRepository.findByUserId(user_parent.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        if (parent.getPrimaryStudentId() == null) parent.setPrimaryStudentId(student.getUserId());

        student.setParent(parent);
        studentRepository.save(student);
    }

    public void changeLink(Long userId, UserDto.ChangeLink userDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));

        Parent parent = parentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        Student student = studentRepository.findById(userDto.getStudentId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));

        parent.setPrimaryStudentId(student.getId());
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
