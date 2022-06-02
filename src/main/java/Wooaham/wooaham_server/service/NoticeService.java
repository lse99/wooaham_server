package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.notice.Notice;
import Wooaham.wooaham_server.domain.notice.Reader;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.Teacher;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.dto.request.NoticeRequest;
import Wooaham.wooaham_server.dto.response.NoticeResponse;
import Wooaham.wooaham_server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final ReaderRepository readerRepository;
    private final JwtService jwtService;

    @Transactional(readOnly = true)
    public List<NoticeResponse> findNotices() {
        UserDto.UserInfo userInfo = jwtService.getUserInfo();

        if (userInfo.getRole().equals(UserType.STUDENT)) {
            Student student = studentRepository.findByUserId(userInfo.getUserId()).get();
            return noticeRepository.findAllByClassCode(student.getClassCode());
        } else if (userInfo.getRole().equals(UserType.TEACHER)) {
            Teacher teacher = teacherRepository.findByUserId(userInfo.getUserId()).get();
            return noticeRepository.findAllByClassCode(teacher.getClassCode());
        } else {
            Parent parent = parentRepository.findByUserId(userInfo.getUserId()).get();
            Student student = studentRepository.findByUserId(parent.getPrimaryStudentId()).get();
            return noticeRepository.findAllByClassCode(student.getClassCode());
        }
    }

    @Transactional(readOnly = true)
    public NoticeResponse findOne(Long noticeId) {
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        return NoticeResponse.of(findNotice);
    }

    public Long addNotice(NoticeRequest req) {
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        Teacher user = teacherRepository.findByUserId(userInfo.getUserId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_TEACHER));
        Notice notice = req.toNotice(user);
        noticeRepository.save(notice);
        return notice.getId();
    }

    public NoticeResponse updateNotice(Long noticeId, NoticeRequest req) {
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        if (req.getTitle() == null && req.getContents() == null) {
            throw new BaseException(ErrorCode.INVALID_MISSING_PARAMETER);
        } else if (req.getContents() == null) {
            findNotice.updateTitle(req.getTitle());
        } else if (req.getTitle() == null) {
            findNotice.updateContents(req.getContents());
        } else {
            findNotice.updateTitle(req.getTitle());
            findNotice.updateContents(req.getContents());
        }
        noticeRepository.save(findNotice);
        return NoticeResponse.of(findNotice);
    }

    public void deleteNotice(Long noticeId) {
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        noticeRepository.delete(findNotice);
    }

    public List<String> findReaders(Long noticeId) {
        List<String> ret = new ArrayList<>();
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        List<Reader> readers = findNotice.getReaders();
        for (Reader r : readers) {
            ret.add(studentRepository.findByClassCodeAndParent(r.getNotice().getUser().getClassCode(), r.getParent())
                    .getUser().getName() + " 부모님");
        }
        return ret;
    }


    public void checkReading(Long noticeId) {
        UserDto.UserInfo userInfo = jwtService.getUserInfo();
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));

        if (userInfo.getRole().equals(UserType.PARENT)) {
            Parent user = parentRepository.findByUserId(userInfo.getUserId()).get();
            if (readerRepository.findByNoticeAndParent(findNotice, user) == null) {
                readerRepository.save(Reader.of(findNotice, user));
            }
        }
    }
}
