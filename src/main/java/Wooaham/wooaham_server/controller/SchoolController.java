package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.SchoolDto;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/schools")
public class SchoolController {

    @GetMapping
    public List<SchoolDto> getSchools() throws Exception {

        StringBuffer result = new StringBuffer();
        String urlStr = "https://open.neis.go.kr/hub/schoolInfo";

        URL url = new URL(urlStr);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n");
        }

        httpURLConnection.disconnect();

        JSONObject jObj;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(result.toString());
        JSONObject parseResponse = (JSONObject) jsonObj.get("schoolInfo");
        JSONArray array = (JSONArray) parseResponse.get("row");

        List<SchoolDto> result3 = new ArrayList<SchoolDto>();

        for(int i = 0; i < array.length(); i++) {
            jObj = (JSONObject) array.get(i);

            SchoolDto schoolDto = new SchoolDto(
                    jObj.get("ATPT_OFCDC_SC_CODE").toString(),
                    jObj.get("SD_SCHUL_CODE").toString(),
                    jObj.get("SCHUL_NM").toString()
            );

            result3.add(schoolDto);
        }

        return result3;
    }
}
