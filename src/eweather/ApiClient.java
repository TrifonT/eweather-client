/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather;


import java.io.*;
import java.util.ArrayList;
import java.net.*;
import com.google.gson.*;
import eweather.io.*;
import eweather.db.*;
import java.util.Date;
import java.util.function.Consumer;
/**
 *
 * @author Trifon
 */
public class ApiClient {

    private final String apiUrl = "http://api.openweathermap.org/data/2.5/group";
    private final String ApiKey = "65c4cd2d1e16f6506911647920f3365a";


    private String CovertIdsToString(ArrayList<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        ids.forEach((id) -> {
            sb.append(id).append(",");
        });
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    private String GetApiUrl(ArrayList<Integer> cityIds) {
        String strIds = CovertIdsToString(cityIds);
        return String.format("%s?id=%s&unit=metric&appid=%s", 
                apiUrl, strIds, ApiKey);
    }

    public String GetApiResponse(ArrayList<Integer> cityIds) 
            throws MalformedURLException, IOException {
        URL url = new URL(GetApiUrl(cityIds));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "eWeather client");    
        
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response.toString();
    }

    private WeatherReport GetWeatherReport(ArrayList<Integer> cityIds) {
        WeatherReport report = null;
        try {
            String jstring = GetApiResponse(cityIds);
            //System.out.println(jstring);
            Gson  gson = new Gson();
           report = gson.fromJson(jstring, WeatherReport.class);
        } catch (JsonSyntaxException | IOException ex) {
            System.out.println(ex.toString());
        }
        return report;
    }    
    
    public ArrayList<Weatherdata> GetWeather(ArrayList<Integer> cityIds)
    {
        ArrayList<Weatherdata> list = new ArrayList<>();
        WeatherReport report = GetWeatherReport(cityIds);
        if(report!=null)
        {
            report.list.forEach((List citydata) -> {
                Weatherdata data = new Weatherdata();
                data.setCityid(citydata.id);
                Date dt = new Date(citydata.dt);
                data.setDt(dt);
                data.setTemp(citydata.main.temp);
                data.setDescription( citydata.weather.get(0).description);
                data.setClouds(citydata.clouds.all);
                data.setWindspeed(citydata.wind.speed);
                data.setRain(citydata.rain);
                data.setSnow(citydata.snow);
                list.add(data);
            }); 
        }        
        return list;
    }
    
}
