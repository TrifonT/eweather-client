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
     * Μεταστρέπει μια λίστα ακεραίων σε ένα String
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
     * Μορφοποιεί την διεύνθυση της κλήσης στο openweather για τον τρέχοντα
     * καιρό
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
     * Μορφοποιεί την διεύνθυση της κλήσης στο openweather για την πρόγνωση
     *
     * @param cityIds
     * @return
     */
    private String getPredictUrl(Long cityId) {
        return String.format("%s?appid=%s&id=%d&units=metric",
                predict_url, ApiKey, cityId);
    }

    /**
     * Λαμβάνει την απάντηση από την κλήση στο API σαν ένα JSON String ``
     *
     * @param cityIds Λίστα από id πόλεων
     * @return ένα JSON String
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
     * Προετοιμάζει την κλήση στο API για τον τρέχοντα καιρό
     *
     * @param cityIds Λίστα με τους κωδικούς των πόλεων
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
     * Προετοιμάζει την κλήση στο API για πρόγνωση
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
     * Μετατρέπει ένα JSON String σε ένα αντικείμενο WeatherReport
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
     * Κάνει κλήση στο API και μετατρέπει το JSON string σε ένα αντικείμενο
     * τύπου WeatherReport για μια λίστα πόλεων
     *
     * @param cityIds Μια λίστα με τως κωδικούς των πόλεων
     * @return Ένα αντικείμενο της κλάσης WeatherReport
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
     * Κάνει κλήση στο API επιστέφει τις προβλέψεις για τις επόμενες 5 ημέρες
     * μέσα σε ένα αντικείμενο WeatherReport
     *
     * @param cityId το ID της πόλης για την οποία θα πάρουμε την πρόβλεψη
     * @return Ένα αντικείμενο της κλάσης WeatherReport
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
