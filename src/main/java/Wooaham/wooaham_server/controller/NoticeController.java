package Wooaham.wooaham_server.controller;

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
        return null;
    }

    @GetMapping("/{idx}")
    public ApiResponse getNoticeDetail(@PathVariable Long noticeId){
        return null;
    }

    @GetMapping("/{idx}/reading")
    public ApiResponse getNoticeReaders(@PathVariable Long noticeId){
        return null;
    }

    @PostMapping("")
    public ApiResponse addNotice(){
        return null;
    }

    @PutMapping("/{idx}")
    public ApiResponse updateNotice(@PathVariable Long noticeId){
        return null;
    }

    @DeleteMapping("/{idx}")
    public ApiResponse deleteNotice(@PathVariable Long noticeId){
        return null;
    }

    @PostMapping("/{idx}/reading")
    public ApiResponse checkNoticeRead(@PathVariable Long noticeId) {
        return null;
    }

}
