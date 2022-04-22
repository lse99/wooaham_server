package Wooaham.wooaham_server.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ScheduleController {

    //변수 serviceKey에 인증키를 넣어주고

    private String serviceKey = "0695d515ad8d408a8d6d011035f09057";

    public void getTimetable(){
//        StringBuffer result = new StringBuffer();
//        try{
//            String urlstr = "";
//        }

    /*
    public void getTimetable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String addr = "http://?ServiceKey=";
        String serviceKey = "자신의 키값 입력";
        String parameter = "";
//        serviceKey = URLEncoder.encode(serviceKey,"utf-8");
        PrintWriter out = response.getWriter();
        parameter = parameter + "&" + "areaCode=1";
        parameter = parameter + "&" + "eventStartDate=20170401";
        parameter = parameter + "&" + "eventEndDate=20171231";
        parameter = parameter + "&" + "pageNo=1&numOfRows=10";
        parameter = parameter + "&" + "MobileOS=ETC";
        parameter = parameter + "&" + "MobileApp=aa";
        parameter = parameter + "&" + "_type=json";



        addr = addr + serviceKey + parameter;
        URL url = new URL(addr);

        InputStream in = url.openStream();
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();

        String data = bos.getOut().toString();
        out.println(data);

        JSONObject json = new JSONObject();
        json.put("data", data);

         */
    }
}

