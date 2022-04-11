package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.request.NoticeRequest;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("")
    public ApiResponse getNotices(@RequestParam String classCode){
        return ApiResponse.success(noticeService.findNotices(classCode));
    }

    @GetMapping("/{id}")
    public ApiResponse getNoticeDetail(@PathVariable Long noticeId){
        return ApiResponse.success(noticeService.findOne(noticeId));
    }

    @GetMapping("/{id}/reading")
    public ApiResponse getNoticeReaders(@PathVariable Long noticeId){
        return null;
    }

    @PostMapping("/{id}")
    public ApiResponse addNotice(@RequestBody NoticeRequest req, @PathVariable Long userId){
        return ApiResponse.success(noticeService.addNotice(userId, req));
    }

    @PutMapping("/{id}")
    public ApiResponse updateNotice(@RequestBody NoticeRequest req, @PathVariable Long noticeId){
        return ApiResponse.success(noticeService.updateNotice(noticeId, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return null;
    }

    @PostMapping("/{id}/reading")
    public ApiResponse checkNoticeRead(@PathVariable Long noticeId) {
        return null;
    }

}
