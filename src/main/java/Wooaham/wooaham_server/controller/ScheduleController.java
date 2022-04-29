package Wooaham.wooaham_server.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import Wooaham.wooaham_server.dto.request.ScheduleRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    @GetMapping("/info/timetable")
    public JSONObject getTimetable(@RequestBody ScheduleRequest req) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/elsTimetable?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + req.getSidoCode() +
                "&SD_SCHUL_CODE=" + req.getSchoolCode() +
                "&AY=2022&SEM=1" +
                "&GRADE=" + req.getGrade() +
                "&CLASS_NM=" + req.getClass_nm() +
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

    @GetMapping("/info/timetable/one-day")
    public JSONObject getTimetableOne(@RequestBody ScheduleRequest req) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/elsTimetable?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + req.getSidoCode() +
                "&SD_SCHUL_CODE=" + req.getSchoolCode() +
                "&AY=2022&SEM=1" +
                "&GRADE=" + req.getGrade() +
                "&CLASS_NM=" + req.getClass_nm() +
                "&TI_FROM_YMD=" + req.getStartDay() +
                "&TI_TO_YMD=" + req.getStartDay();
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

    @GetMapping("/info/lunchtable")
    public JSONObject getLunchtable(@RequestBody ScheduleRequest req) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/mealServiceDietInfo?" +
                "KEY=0695d515ad8d408a8d6d011035f09057" +
                "&Type=json" +
                "&pIndex=1&pSize=100" +
                "&ATPT_OFCDC_SC_CODE=" + req.getSidoCode() +
                "&SD_SCHUL_CODE=" + req.getSchoolCode() +
                "&MLSV_FROM_YMD=" + req.getStartDay() +
                "&MLSV_TO_YMD=" + req.getEndDay();
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
}