package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.request.AlarmRequest;
import Wooaham.wooaham_server.dto.response.ApiResponse;
import Wooaham.wooaham_server.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("")
    public ApiResponse getAlarms(@RequestParam Long userId){
        return ApiResponse.success(alarmService.findAlarms(userId));
    }

    @PostMapping("/{userId}")
    public ApiResponse addAlarm(@RequestBody AlarmRequest req, @PathVariable Long userId){
        return ApiResponse.success(alarmService.addAlarm(userId, req));
    }

    @PutMapping("/{alarmId}")
    public ApiResponse updateAlarm(@RequestBody AlarmRequest req, @PathVariable Long alarmId){
        return ApiResponse.success(alarmService.updateAlarm(alarmId, req));
    }

    @DeleteMapping("/{alarmId}")
    public ApiResponse deleteAlarm(@PathVariable Long alarmId){
        alarmService.deleteAlarm(alarmId);
        return ApiResponse.success(null);
    }

}
