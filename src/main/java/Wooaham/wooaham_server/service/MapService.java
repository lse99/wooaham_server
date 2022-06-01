package Wooaham.wooaham_server.service;

import Wooaham.wooaham_server.domain.BaseException;
import Wooaham.wooaham_server.domain.Location;
import Wooaham.wooaham_server.domain.type.ErrorCode;
import Wooaham.wooaham_server.domain.user.Parent;
import Wooaham.wooaham_server.domain.user.Student;
import Wooaham.wooaham_server.domain.user.User;
import Wooaham.wooaham_server.dto.StoreDto;
import Wooaham.wooaham_server.dto.UserDto;
import Wooaham.wooaham_server.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MapService {
    private final JwtService jwtService;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final LocationRepository locationRepository;

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

    public void getResponseToExcel(
            HttpServletResponse response,
            Integer page
    ) throws IOException, ParseException {
        List<StoreDto> result = getResponse(page);

        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // Header
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);

        Cell headerCell1 = headerRow.createCell(0);
        headerCell1.setCellValue("id");

        Cell headerCell2 = headerRow.createCell(1);
        headerCell2.setCellValue("lat");

        Cell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("lng");

        Cell headerCell4 = headerRow.createCell(3);
        headerCell4.setCellValue("fac_nam");

        Cell headerCell5 = headerRow.createCell(4);
        headerCell5.setCellValue("fac_tel");

        Cell headerCell6 = headerRow.createCell(5);
        headerCell6.setCellValue("fac_o_add");

        Cell headerCell7 = headerRow.createCell(6);
        headerCell7.setCellValue("cat_nam");

        Cell headerCell8 = headerRow.createCell(7);
        headerCell8.setCellValue("fac_n_add");


        // Body
        for (StoreDto p : result) {
            Row bodyRow = sheet.createRow(rowIndex++);

            Cell bodyCell1 = bodyRow.createCell(0);
            bodyCell1.setCellValue(p.getId());

            Cell bodyCell2 = bodyRow.createCell(1);
            bodyCell2.setCellValue(p.getLat());

            Cell bodyCell3 = bodyRow.createCell(2);
            bodyCell3.setCellValue(p.getLng());

            Cell bodyCell4 = bodyRow.createCell(3);
            bodyCell4.setCellValue(p.getFac_nam());

            Cell bodyCell5 = bodyRow.createCell(4);
            bodyCell5.setCellValue(p.getFac_tel());

            Cell bodyCell6 = bodyRow.createCell(5);
            bodyCell6.setCellValue(p.getFac_o_add());

            Cell bodyCell7 = bodyRow.createCell(6);
            bodyCell7.setCellValue(p.getCat_nam());

            Cell bodyCell8 = bodyRow.createCell(7);
            bodyCell8.setCellValue(p.getFac_n_add());
        }

        // Response
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=Purchase_List.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public List<StoreDto.Simple> getStores() {
        return storeRepository.findAll().stream()
                .map(StoreDto.Simple::from)
                .collect(Collectors.toList());
    }

    public StoreDto.Detail getStore(String id) {
        return storeRepository.findById(id)
                .map(StoreDto.Detail::from)
                .orElseThrow();
    }

    public Location getStudentLocation(Long id) {

        UserDto.UserInfo userInfoByJwt = jwtService.getUserInfo();

        if (!Objects.equals(userInfoByJwt.getUserId(), id))
            throw new BaseException(ErrorCode.INVALID_USER_JWT);

        User user = userRepository.findActiveUser(id);

        Parent parent = parentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BaseException(ErrorCode.NOTFOUND_PARENT));

        Long primaryStudentId = parent.getPrimaryStudentId();
        if (primaryStudentId == null) throw new BaseException(ErrorCode.NOTFOUND_CHILDREN);

        if (locationRepository.getRecentStudentLocation(primaryStudentId).size() == 0)
            throw new BaseException(ErrorCode.NOTFOUND_STUDENT_LOCATION);
        return locationRepository.getRecentStudentLocation(primaryStudentId).get(0);
    }

}
