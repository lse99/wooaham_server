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
    public ApiResponse getNotices(){
        return ApiResponse.success(noticeService.findNotices());
    }

    @GetMapping("/{noticeId}")
    public ApiResponse getNoticeDetail(@PathVariable Long noticeId){
        return ApiResponse.success(noticeService.findOne(noticeId));
    }

    @GetMapping("/{noticeId}/reading")
    public ApiResponse getNoticeReaders(@PathVariable Long noticeId){
        return ApiResponse.success(noticeService.findReaders(noticeId));
    }

    @PostMapping("")
    public ApiResponse addNotice(@RequestBody NoticeRequest req){
        return ApiResponse.success(noticeService.addNotice(req));
    }

    @PutMapping("/{noticeId}")
    public ApiResponse updateNotice(@RequestBody NoticeRequest req, @PathVariable Long noticeId){
        return ApiResponse.success(noticeService.updateNotice(noticeId, req));
    }

    @DeleteMapping("/{noticeId}")
    public ApiResponse deleteNotice(@PathVariable Long noticeId){
        noticeService.deleteNotice(noticeId);
        return ApiResponse.success(null);
    }

    @PostMapping("/{noticeId}/reading")
    public ApiResponse checkNoticeRead(@PathVariable Long noticeId) {
        noticeService.checkReading(noticeId);
        return ApiResponse.success(null);
    }

}
