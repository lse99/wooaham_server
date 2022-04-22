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
    public ApiResponse getSchoolHomework(@RequestParam Long userId){
        return ApiResponse.success(homeworkService.findSchoolHomework(userId));
    }

    @GetMapping("/academy")
    public ApiResponse getAcademyHomework(@RequestParam Long userId){
        return ApiResponse.success(homeworkService.findAcademyHomework(userId));
    }

    @PostMapping("/{userId}")
    public ApiResponse addHomework(@RequestBody HomeworkRequest req, @PathVariable Long userId){
        return ApiResponse.success(homeworkService.addHomework(userId, req));
    }

    @PutMapping("/{hwId}")
    public ApiResponse updateHomework(@RequestBody HomeworkRequest req, @PathVariable Long hwId){
        return null;
    }

    @DeleteMapping("/{hwId}")
    public ApiResponse deleteHomework(@PathVariable Long hwId){
        return null;
    }

    @PostMapping("/check/{hwId}")
    public ApiResponse checkHomework(@RequestBody HomeworkRequest req, @PathVariable Long hwId){
        return null;
    }


}
