package Wooaham.wooaham_server.controller;

import Wooaham.wooaham_server.dto.LocationDto;
import Wooaham.wooaham_server.dto.StoreDto;
import Wooaham.wooaham_server.dto.response.ApiResponse;
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

   @GetMapping("/stores")
    public ApiResponse<List<StoreDto.Simple>> getStores(){
        return ApiResponse.success(mapService.getStores());
    }

    @GetMapping("/stores/{storeId}")
    public ApiResponse<StoreDto.Detail> getStore(@PathVariable(name = "storeId") String id){
        return ApiResponse.success(mapService.getStore(id));
    }
}


