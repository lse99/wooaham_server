package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.domain.SchoolInfo;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/schools")
    public ApiResponse<List<SchoolInfo>> getSchoolInfo(@RequestParam(name = "name") String schoolName){
        return ApiResponse.success(schoolService.getSchoolInfo(schoolName));
    }
}
