package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.notice.Notice;
import Wooaham.wooaham_server.domain.notice.Reader;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Teacher;
import Wooaham.wooaham_server.dto.request.NoticeRequest;
import Wooaham.wooaham_server.dto.response.NoticeResponse;
import Wooaham.wooaham_server.repository.NoticeRepository;
import Wooaham.wooaham_server.repository.ReaderRepository;
import Wooaham.wooaham_server.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final TeacherRepository teacherRepository;
    private final ReaderRepository readerRepository;

    @Transactional(readOnly = true)
    public List<NoticeResponse> findNotices(String classCode){
        return noticeRepository.findAllByClassCode(classCode);
    }

    @Transactional(readOnly = true)
    public Notice findOne(Long noticeId){
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
    }

    public Long addNotice(Long userId, NoticeRequest req){
        Teacher user = teacherRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_TEACHER));
        Notice notice = req.toNotice(user);
        noticeRepository.save(notice);
        return notice.getId();
    }

    public NoticeResponse updateNotice(Long noticeId, NoticeRequest req){
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        if(req.getTitle()==null && req.getContents()==null){
            throw new BaseException(ErrorCode.INVALID);
        } else if(req.getContents()==null){
            findNotice.updateTitle(req.getTitle());
        } else if(req.getTitle()==null){
            findNotice.updateContents(req.getContents());
        } else{
            findNotice.updateTitle(req.getTitle());
            findNotice.updateContents(req.getContents());
        }
        noticeRepository.save(findNotice);
        return NoticeResponse.of(findNotice);
    }

    public void deleteNotice(Long noticeId){
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        noticeRepository.delete(findNotice);
    }

    public void findReaders(Long noticeId, Long parentId){
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));

    }

    public void checkReading(Long noticeId, Long parentId){
        Notice findNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_NOTICE));
        //Parent
    }

}
