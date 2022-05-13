package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.request.PhoneTimeAlarmRequest;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.PhoneTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/phone-time")
public class PhoneTimeController {
    private final PhoneTimeService phoneTimeService;

    @GetMapping("")
    public ApiResponse getPhoneUsageTime(){
        return ApiResponse.success(phoneTimeService.findPhoneUsageTime());
    }

    @PutMapping("")
    public ApiResponse updatePhoneUsageTime(){
        return null;
    }

    @GetMapping("/alarm")
    public ApiResponse getPhoneUsageTimeAlarm(){
        return ApiResponse.success(phoneTimeService.findPhoneTimeAlarm());
    }

    @PutMapping("/alarm")
    public ApiResponse modifyPhoneUsageTimeAlarm(@RequestBody PhoneTimeAlarmRequest req){
        return ApiResponse.success(phoneTimeService.addPhoneTimeAlarm(req));
    }

    @DeleteMapping("/alarm")
    public ApiResponse deletePhoneUsageTimeAlarm(){
        return null;
    }
}
