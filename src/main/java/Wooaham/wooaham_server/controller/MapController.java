package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.LocationDto;
import Wooaham.wooaham_server.dto.StoreDto;
import Wooaham.wooaham_server.service.MapService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Style;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/response")
    public List<StoreDto> getResponse(@RequestParam(name = "page") Integer page) throws IOException, ParseException {
        return mapService.getResponse(page);
    }

    @GetMapping("/excel")
    public void getResponseToExcel(
            HttpServletResponse response,
            @RequestParam(name = "page") Integer page
    ) throws IOException, ParseException {
        mapService.getResponseToExcel(response, page);
    }

   /* @GetMapping("/stores")
    public List<StoreDto> getStores(){
        return mapService.getStores();
    }*/

    @PostMapping("/stores/detail")
    public JSONObject getStore(@RequestBody LocationDto location) throws IOException, ParseException {
        StringBuilder result = new StringBuilder();

        String urlStr = "https://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_P_MGPRTFA" +
                "&key=947ADDDF-C1E0-3D54-893B-9BC84D3A44A6" +
                "&geomfilter=POINT(" + location.getLng() + "%20" + location.getLat() + ")";

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


