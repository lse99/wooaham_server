package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Homework;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.HomeworkType;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.dto.request.HomeworkRequest;
import Wooaham.wooaham_server.dto.response.HomeworkResponse;
import Wooaham.wooaham_server.repository.HomeworkRepository;
import Wooaham.wooaham_server.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public List<HomeworkResponse> findSchoolHomework(Long userId){
        Student user = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));
        List<Homework> hwList = homeworkRepository.findByUserAndType(user, HomeworkType.SCHOOL);
        List<HomeworkResponse> responses = new ArrayList<>();
        for (Homework h : hwList)
            responses.add(HomeworkResponse.of(h));
        return responses;
    }

    @Transactional(readOnly = true)
    public List<HomeworkResponse> findAcademyHomework(Long userId){
        Student user = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));
        List<Homework> hwList = homeworkRepository.findByUserAndType(user, HomeworkType.ACADEMY);
        List<HomeworkResponse> responses = new ArrayList<>();
        for (Homework h : hwList)
            responses.add(HomeworkResponse.of(h));
        return responses;
    }

    public Long addHomework(Long userId, HomeworkRequest req){
        Student user = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_STUDENT));
        Homework homework = req.toHomework(user);
        homeworkRepository.save(homework);
        return homework.getId();
    }

    public HomeworkResponse updateHomework(Long homeworkId, HomeworkRequest req){
        Homework findHomework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_HOMEWORK));
        if (req.getTitle() == null) {
            throw new BaseException(ErrorCode.INVALID_MISSING_PARAMETER);
        } else {
            findHomework.updateTitle(req.getTitle());
        }
        homeworkRepository.save(findHomework);
        return HomeworkResponse.of(findHomework);
    }

    public void deleteHomework(Long homeworkId){
        Homework findHomework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_HOMEWORK));
        homeworkRepository.delete(findHomework);
    }

    public HomeworkResponse checkHomework(Long homeworkId){
        Homework findHomework = homeworkRepository.findById(homeworkId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_HOMEWORK));
        findHomework.updateCheck();
        return HomeworkResponse.of(findHomework);
    }
}
