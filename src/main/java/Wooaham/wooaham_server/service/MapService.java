package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MapService {
    public List<StoreDto> getResponse(Integer page) throws IOException, ParseException {
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

        JSONObject jsonObject = (JSONObject) parser.parse(result.toString());
        JSONObject jsonObject2 = (JSONObject) jsonObject.get("response");
        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("result");
        JSONObject jsonObject4 = (JSONObject) jsonObject3.get("featureCollection");
        JSONArray jsonArray = (JSONArray) jsonObject4.get("features");

        List<StoreDto> resultList = new ArrayList<>();

        for (Object o : jsonArray) {
            JSONObject obj = (JSONObject) o;
            String id = (String) obj.get("id");

            JSONObject geometry = (JSONObject) obj.get("geometry");
            JSONArray coordinates = (JSONArray) geometry.get("coordinates");

            JSONObject properties = (JSONObject) obj.get("properties");
            String fac_nam = (String) properties.get("fac_nam");
            String fac_tel = (String) properties.get("fac_tel");
            String fac_o_add = (String) properties.get("fac_o_add");
            String cat_nam = (String) properties.get("cat_nam");
            String fac_n_add = (String) properties.get("fac_n_add");

            StoreDto element = new StoreDto(
                    id,
                    (Double) coordinates.get(0),
                    (Double) coordinates.get(1),
                    fac_nam,
                    fac_tel,
                    fac_o_add,
                    cat_nam,
                    fac_n_add
            );
            resultList.add(element);
        }
        return resultList;
    }

    public void getResponseToExcel(Integer page) throws IOException, ParseException {
        List<StoreDto> response = getResponse(page);
    }

}
