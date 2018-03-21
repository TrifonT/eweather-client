/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import eweather.io.WeatherReport;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trifon
 */
public class WebApiClient {

    private static final Logger LOGGER
            = Logger.getLogger(WebApiClient.class.getName());

    private final String weather_url
            = "http://api.openweathermap.org/data/2.5/group";
    private final String predict_url
            = "http://api.openweathermap.org/data/2.5/forecast";
    private final String ApiKey = "65c4cd2d1e16f6506911647920f3365a";

    /**
     * ����������� ��� ����� �������� �� ��� String
     *
     * @param ids
     * @return
     */
    private String covertIdsToString(List<Long> ids) {
        StringBuilder sb = new StringBuilder();
        ids.forEach((id) -> {
            sb.append(id).append(",");
        });
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }

    /**
     * ���������� ��� ��������� ��� ������ ��� openweather ��� ��� ��������
     * �����
     *
     * @param cityIds
     * @return
     */
    private String getWeatherUrl(List<Long> cityIds) {
        String strIds = covertIdsToString(cityIds);
        return String.format("%s?appid=%s&id=%s&units=metric",
                weather_url, ApiKey, strIds);
    }

    /**
     * ���������� ��� ��������� ��� ������ ��� openweather ��� ��� ��������
     *
     * @param cityIds
     * @return
     */
    private String getPredictUrl(Long cityId) {
        return String.format("%s?appid=%s&id=%d&units=metric",
                predict_url, ApiKey, cityId);
    }

    /**
     * �������� ��� �������� ��� ��� ����� ��� API ��� ��� JSON String ``
     *
     * @param cityIds ����� ��� id ������
     * @return ��� JSON String
     * @throws MalformedURLException
     * @throws IOException
     */
    private String getJsonRespone(String strurl)
            throws MalformedURLException, IOException {

        URL url = new URL(strurl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "eWeather client");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return response.toString();
    }

    /**
     * ������������ ��� ����� ��� API ��� ��� �������� �����
     *
     * @param cityIds ����� �� ���� �������� ��� ������
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private String getWeatherResponse(List<Long> cityIds)
            throws MalformedURLException, IOException {
        String urlstring = getWeatherUrl(cityIds);

        return getJsonRespone(urlstring);
    }

    /**
     * ������������ ��� ����� ��� API ��� ��������
     *
     * @param cityId
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    private String getPrognosisResponse(Long cityId)
            throws MalformedURLException, IOException {
        String predictstring = getPredictUrl(cityId);
        return getJsonRespone(predictstring);
    }

    /**
     * ���������� ��� JSON String �� ��� ����������� WeatherReport
     *
     * @param jstring
     * @return
     */
    private WeatherReport convertToWeatherReport(String jstring) {

        Gson gson = new Gson();
        WeatherReport report = gson.fromJson(jstring, WeatherReport.class);
        return report;
    }

    /**
     * ����� ����� ��� API ��� ���������� �� JSON string �� ��� �����������
     * ����� WeatherReport ��� ��� ����� ������
     *
     * @param cityIds ��� ����� �� ��� �������� ��� ������
     * @return ��� ����������� ��� ������ WeatherReport
     */
    public WeatherReport getWeather(List<Long> cityIds) {
        WeatherReport report = null;
        try {
            String jstring = getWeatherResponse(cityIds);
            report = convertToWeatherReport(jstring);
        } catch (JsonSyntaxException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return report;
    }

    /**
     * ����� ����� ��� API ��������� ��� ���������� ��� ��� �������� 5 ������
     * ���� �� ��� ����������� WeatherReport
     *
     * @param cityId �� ID ��� ����� ��� ��� ����� �� ������� ��� ��������
     * @return ��� ����������� ��� ������ WeatherReport
     */
    public WeatherReport getPrognosis(Long cityId) {
        WeatherReport report = null;
        try {
            String jstring = getPrognosisResponse(cityId);
            report = convertToWeatherReport(jstring);
        } catch (JsonSyntaxException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return report;
    }

}
