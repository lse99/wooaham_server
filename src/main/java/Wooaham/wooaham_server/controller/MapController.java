package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.LocationDto;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/maps")
public class MapController {

    @GetMapping("/stores")
    public JSONObject getStores(@RequestParam(name = "page") Integer page) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_P_MGPRTFA" +
                "&key=947ADDDF-C1E0-3D54-893B-9BC84D3A44A6" +
                "&geomfilter=BOX(124,33,131,43)" +
                "&page=" + page +
                "&size=1000";
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

    @PostMapping("/stores/detail")
    public JSONObject getStore(@RequestBody LocationDto location) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_P_MGPRTFA" +
                "&key=947ADDDF-C1E0-3D54-893B-9BC84D3A44A6" +
                "&geomfilter=POINT(" + location.getLng() + "%20" + location.getLat()+ ")";

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


