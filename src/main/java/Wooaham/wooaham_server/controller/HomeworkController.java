package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.request.HomeworkRequest;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info/hw")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping("/school")
    public ApiResponse getSchoolHomework(){
        return ApiResponse.success(homeworkService.findSchoolHomework());
    }

    @GetMapping("/academy")
    public ApiResponse getAcademyHomework(){
        return ApiResponse.success(homeworkService.findAcademyHomework());
    }

    @PostMapping("/{userId}")
    public ApiResponse addHomework(@RequestBody HomeworkRequest req){
        return ApiResponse.success(homeworkService.addHomework(req));
    }

    @PutMapping("/{hwId}")
    public ApiResponse updateHomework(@RequestBody HomeworkRequest req, @PathVariable Long hwId){
        return ApiResponse.success(homeworkService.updateHomework(hwId, req));
    }

    @DeleteMapping("/{hwId}")
    public ApiResponse deleteHomework(@PathVariable Long hwId){
        homeworkService.deleteHomework(hwId);
        return ApiResponse.success(null);
    }

    @PostMapping("/check/{hwId}")
    public ApiResponse checkHomework(@PathVariable Long hwId) {
        return ApiResponse.success(homeworkService.checkHomework(hwId));
    }
}
