package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.Icon;
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
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final IconRepository iconRepository;

    @Transactional(readOnly = true)
    public List<UserDto> getUsers(){
        return userRepository.findAll().stream()
                .filter(User::isActivated)
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getUser(Long userId){
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<UserDto.Child> getChildren(Long userId){
        return studentRepository.findAllByParentId(userId).stream()
                .map(UserDto.Child::from)
                .collect(Collectors.toList());
    }

    public void registerName(Long userId, UserDto.RegisterName userDto){
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(userDto.getName());
        userRepository.save(user);
    }

    public void registerUserRole(Long userId, UserDto.RegisterRole userDto){
        UserType role = userDto.getRole();

        User user = userRepository.findById(userId).orElseThrow();
        user.setRole(role);

        switch (role){
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
                throw new RuntimeException();
        }
    }

    public void link(Long userId, UserDto.Link userDto){
        Student student = studentRepository.findByUserId(userId).orElseThrow();
        Parent parent = parentRepository.findById(userDto.getParentId()).orElseThrow();

        if(parent.getPrimaryStudentId() == null) parent.setPrimaryStudentId(student.getId());

        student.setParent(parent);
        studentRepository.save(student);
    }

    public void changeLink(Long userId, UserDto.ChangeLink userDto){
        Parent parent = parentRepository.findByUserId(userId).orElseThrow();
        parent.setPrimaryStudentId(userDto.getStudentId());
        parentRepository.save(parent);
    }

    public void updateUserIcon(Long userId, UserDto.UpdateIcon userDto){
        User user = userRepository.findById(userId).orElseThrow();
        Icon icon = iconRepository.findById(userDto.getIconId()).orElseThrow();

        user.setIconId(icon.getIconId());
        userRepository.save(user);
    }

    public void deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow();

        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
