package Wooaham.wooaham_server.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.type.UserType;
import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.Teacher;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.repository.ParentRepository;
import Wooaham.wooaham_server.repository.StudentRepository;
import Wooaham.wooaham_server.repository.TeacherRepository;
import Wooaham.wooaham_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;

    @GetMapping("/info/timetable/{userId}")
    public JSONObject getTimetable(@PathVariable Long userId) throws IOException, ParseException {
        List<String> info = getInfo(userId);

        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/elsTimetable?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + info.get(0) +
                "&SD_SCHUL_CODE=" + info.get(1) +
                "&AY=2022&SEM=1" +
                "&GRADE=" + info.get(2) +
                "&CLASS_NM=" + info.get(3) +
                "&TI_FROM_YMD=20220425" +
                "&TI_TO_YMD=20220429";
        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }
        urlConnection.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result.toString());

        return obj;
    }

    @GetMapping("/info/timetable/one-day/{userId}")
    public JSONObject getTimetableOne(@RequestParam("startDay") String startDay,
                                      @PathVariable Long userId) throws IOException, ParseException {
        List<String> info = getInfo(userId);

        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/elsTimetable?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + info.get(0) +
                "&SD_SCHUL_CODE=" + info.get(1) +
                "&AY=2022&SEM=1" +
                "&GRADE=" + info.get(2) +
                "&CLASS_NM=" + info.get(3) +
                "&TI_FROM_YMD=" + startDay +
                "&TI_TO_YMD=" + startDay;
        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }
        urlConnection.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result.toString());

        return obj;
    }

    @GetMapping("/info/lunchtable/{userId}")
    public JSONObject getLunchtable(@RequestParam("startDay") String startDay,
                                    @RequestParam("endDay") String endDay,
                                    @PathVariable Long userId) throws IOException, ParseException {
        List<String> info = getInfo(userId);

        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/mealServiceDietInfo?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + info.get(0) +
                "&SD_SCHUL_CODE=" + info.get(1) +
                "&MLSV_FROM_YMD=" + startDay +
                "&MLSV_TO_YMD=" + endDay;
        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }
        urlConnection.disconnect();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(result.toString());

        JSONArray mealInfo = (JSONArray) obj.get("mealServiceDietInfo");
        JSONObject first = (JSONObject) mealInfo.get(0);
        JSONArray head = (JSONArray) first.get("head");
        JSONObject second = (JSONObject) head.get(1);
        JSONObject res = (JSONObject) second.get("RESULT");
        String code = res.get("CODE") + "";

        if(code.equals("INFO-000")){
            JSONObject tmp = (JSONObject) mealInfo.get(1);
            JSONArray row = (JSONArray) tmp.get("row");
            for(int i = 0; i < row.size(); i++){
                JSONObject now = (JSONObject) row.get(i);
                String str = now.get("DDISH_NM") + "";
                str = str.replace("<br/>", "\n");
                now.replace("DDISH_NM", str);
            }
        }

        return obj;
    }

    public List<String> getInfo(Long userId){
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_USER));
        String sidoCode;
        String schoolCode;
        String grade;
        String class_nm;
        if(user.getRole()== UserType.PARENT){
            Parent parent = parentRepository.findByUserId(userId).get();
            Student student = studentRepository.findById(parent.getPrimaryStudentId()).get();
            sidoCode= student.getOfficeCode();
            schoolCode= student.getSchoolCode();
            grade = String.valueOf(student.getGrade());
            class_nm = String.valueOf(student.getClassNum());
        }
        else if(user.getRole()==UserType.STUDENT){
            Student student = studentRepository.findByUserId(userId).get();
            sidoCode= student.getOfficeCode();
            schoolCode= student.getSchoolCode();
            grade = String.valueOf(student.getGrade());
            class_nm = String.valueOf(student.getClassNum());
        }
        else{
            Teacher teacher = teacherRepository.findByUserId(userId).get();
            sidoCode= teacher.getOfficeCode();
            schoolCode= teacher.getSchoolCode();
            grade = String.valueOf(teacher.getGrade());
            class_nm = String.valueOf(teacher.getClassNum());
        }

        List<String> info = new ArrayList<>();
        info.add(sidoCode);
        info.add(schoolCode);
        info.add(grade);
        info.add(class_nm);

        return info;
    }
}