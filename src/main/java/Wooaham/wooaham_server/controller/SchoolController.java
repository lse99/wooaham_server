package Wooaham.wooaham_server.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class SchoolController {

    @GetMapping("/schools")
    public JSONObject getSchools() throws IOException, ParseException{

        StringBuilder result = new StringBuilder();

        String urlStr = "https://open.neis.go.kr/hub/schoolInfo?" +
                "KEY=6434846502e44fd39ef97ff67f7371d4" +
                "&Type=json" +
                "&pIndex=1&pSize=100";

        URL url = new URL(urlStr);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine).append("\n\r");
        }
        httpURLConnection.disconnect();

        JSONParser parser = new JSONParser();

        return (JSONObject) parser.parse(result.toString());
    }
}
