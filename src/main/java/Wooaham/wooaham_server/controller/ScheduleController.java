package Wooaham.wooaham_server.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
    public JSONObject getTimetable(@RequestParam("startDay") String startDay,
                                   @PathVariable Long userId) throws IOException, ParseException {
        List<String> info = getInfo(userId);

        StringBuilder result = new StringBuilder();

        List<String> week = getWeek(startDay);

        String urlStr = "https://open.neis.go.kr/hub/elsTimetable?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + info.get(0) +
                "&SD_SCHUL_CODE=" + info.get(1) +
                "&AY=2022&SEM=1" +
                "&GRADE=" + info.get(2) +
                "&CLASS_NM=" + info.get(3) +
                "&TI_FROM_YMD=" + week.get(0) +
                "&TI_TO_YMD=" + week.get(4);
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


        JSONArray timeInfo = (JSONArray) obj.get("elsTimetable");
        if (timeInfo != null) {
            JSONObject first = (JSONObject) timeInfo.get(0);
            JSONArray head = (JSONArray) first.get("head");
            JSONObject second = (JSONObject) head.get(1);
            JSONObject res = (JSONObject) second.get("RESULT");
            String code = res.get("CODE") + "";

            if (code.equals("INFO-000")) {
                second = (JSONObject) head.get(0);
                JSONObject tmp = (JSONObject) timeInfo.get(1);
                JSONArray row = (JSONArray) tmp.get("row");

                JSONArray arr = new JSONArray();
                JSONArray mon = new JSONArray();
                JSONArray tue = new JSONArray();
                JSONArray wed = new JSONArray();
                JSONArray thu = new JSONArray();
                JSONArray fri = new JSONArray();
                for (int i = 0; i < row.size(); i++) {
                    JSONObject now = (JSONObject) row.get(i);
                    String today = now.get("ALL_TI_YMD") + "";
                    now.remove("ATPT_OFCDC_SC_NM");
                    now.remove("ATPT_OFCDC_SC_CODE");
                    now.remove("SD_SCHUL_CODE");
                    now.remove("AY");
                    now.remove("SEM");
                    now.remove("LOAD_DTM");
                    if(today.equals(week.get(0))) mon.add(now);
                    else if(today.equals(week.get(1))) tue.add(now);
                    else if(today.equals(week.get(2))) wed.add(now);
                    else if (today.equals(week.get(3))) thu.add(now);
                    else fri.add(now);
                }
                arr.add(mon); arr.add(tue); arr.add(wed); arr.add(thu); arr.add(fri);
                row.clear();
                row.addAll(arr);
            }
        }
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
                                    @PathVariable Long userId) throws IOException, ParseException {
        List<String> info = getInfo(userId);

        StringBuilder result = new StringBuilder();

        List<String> week = getWeek(startDay);

        String urlStr = "https://open.neis.go.kr/hub/mealServiceDietInfo?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + info.get(0) +
                "&SD_SCHUL_CODE=" + info.get(1) +
                "&MLSV_FROM_YMD=" + week.get(0) +
                "&MLSV_TO_YMD=" + week.get(4);
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
        if(mealInfo!=null) {
            JSONObject first = (JSONObject) mealInfo.get(0);
            JSONArray head = (JSONArray) first.get("head");
            JSONObject second = (JSONObject) head.get(1);
            JSONObject res = (JSONObject) second.get("RESULT");
            String code = res.get("CODE") + "";

            if (code.equals("INFO-000")) {
                second = (JSONObject) head.get(0);
                JSONObject tmp = (JSONObject) mealInfo.get(1);
                JSONArray row = (JSONArray) tmp.get("row");

                JSONArray arr = new JSONArray();
                int idx = 0;
                for (int i = 0; i < row.size(); i++) {
                    JSONObject now = (JSONObject) row.get(i);
                    if (week.get(idx).equals(now.get("MLSV_YMD"))) {
                        String str = now.get("DDISH_NM") + "";
                        str = str.replace("<br/>", "\n");
                        now.replace("DDISH_NM", str);
                        arr.add(now);
                        idx++;
                    } else {
                        String str = "{\"MLSV_YMD\":\"" + week.get(idx) + "\",\"DDISH_NM\":\"오늘은 급식이 없습니다!\"}";
                        JSONObject jobj = (JSONObject) new JSONParser().parse(str);
                        arr.add(jobj);

                        str = now.get("DDISH_NM") + "";
                        str = str.replace("<br/>", "\n");
                        now.replace("DDISH_NM", str);
                        arr.add(now);
                        idx += 2;
                    }
                }
                if (idx < 4) {
                    for (int i = idx; i < 5; i++) {
                        String str = "{\"MLSV_YMD\":\"" + week.get(i) + "\",\"DDISH_NM\":\"오늘은 급식이 없습니다!\"}";
                        JSONObject jobj = (JSONObject) new JSONParser().parse(str);
                        arr.add(jobj);
                    }
                }
                row.clear();
                row.addAll(arr);
            }
        }
        return obj;
    }

    @GetMapping("/info/lunchtable/one-day/{userId}")
    public JSONObject getLunchtableOne(@RequestParam("startDay") String startDay,
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
                "&MLSV_TO_YMD=" + startDay;
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

    public List<String> getWeek(@RequestParam String day){
        int month = Integer.parseInt(day.substring(4, 6));
        int dayOfMonth = Integer.parseInt(day.substring(6));
        LocalDate date = LocalDate.of(2022, month, dayOfMonth);
        int value = date.getDayOfWeek().getValue();
        int m_num = value - 1;
        LocalDate monday = date.minusDays(m_num);
        LocalDate tuesday = monday.plusDays(1);
        LocalDate wednesday = monday.plusDays(2);
        LocalDate thursday = monday.plusDays(3);
        LocalDate friday = monday.plusDays(4);

        String m = monday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String t1 = tuesday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String w = wednesday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String t2 = thursday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String f = friday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        List<String> s = new ArrayList<>();
        s.add(m);
        s.add(t1);
        s.add(w);
        s.add(t2);
        s.add(f);
        return s;
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