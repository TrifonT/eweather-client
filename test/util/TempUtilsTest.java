/*
 *  � ������� ����� ����� ����� ��� �������� �������� 
 *  ��� ������ ��� ��������� �������� ���240 ���
 *  �������� ��� ���
 *  ������� ���
 *  ��������� ��������
 *  �������������� ������
 */
package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Trifon
 */
public class TempUtilsTest {

    public TempUtilsTest() {
    }

    /**
     * ������� ��� GetWeatherForCities ��� �� ������ �� ��� ������ 735914 ����
     * ����� �� ���������� ��� ���������� � ������������ �Katerini�.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWeatherForCities() throws Exception {

        System.out.println("getWeatherForCities");
        String baseUrl = "http://api.openweathermap.org/data/2.5/group";
        String apiKey = "65c4cd2d1e16f6506911647920f3365a";
        List<String> cityIds = Arrays.asList("735914");

        TempUtils instance = new TempUtils();
        String expResult = "Katerini";
        String result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);

        /*������� �� �� String ��� ���������� �������� ��� ���� "Katerini"*/
        if (result.contains("Katerini")) {
            result = "Katerini";
        }
        assertEquals(expResult, result);

    }

    /**
     * ������� ��� GetWeatherForCities ��� �� ������ �� ����� ������ ���� �����
     * �� ���������� ��� ���������� ��� �� �������� 3 ������
     *
     * @throws Exception
     */
    @Test
    public void testGetWeatherForCitiesCount() throws Exception {

        System.out.println("getWeatherForCities");
        String baseUrl = "http://api.openweathermap.org/data/2.5/group";
        String apiKey = "65c4cd2d1e16f6506911647920f3365a";
        List<String> cityIds = Arrays.asList("735914", "734077", "264371");

        TempUtils instance = new TempUtils();
        String expResult = "3";
        String result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);

        /*��������� �� ������ �������� ������ ��� ��� �������� cnt ��� Json
        Object*/
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(result);
        JsonObject jo = je.getAsJsonObject();
        result = jo.get("cnt").getAsString();

        assertEquals(expResult, result);

    }

    /**
     * ������� ��� GetWeatherForCities ��� �� ������ �� ���������������� baseURL
     * �� ��������� �������� � �������� MalformedExcpetion
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetWeatherForCitiesMalformedException() throws Exception {
        System.out.println("getWeatherForCities");
        String baseUrl = "xttp://api.openweathermap.org/data/2.5/group";
        String apiKey = "65c4cd2d1e16f6506911647920f3365a";
        List<String> cityIds = Arrays.asList("735914", "734077", "264371");

        TempUtils instance = new TempUtils();
        String expResult = "OK";
        String result = "";

        try {
            result = instance.getWeatherForCities(baseUrl, apiKey, cityIds);
        } /* �� ��� ���� ����������� ����� �� URL �� ������ �� ������������ 
        � MalformedURLException . �� ��������� ��������� �� ������ �� 
        ��������� ����������*/ catch (MalformedURLException e) {
            result = "OK";
        }

        assertEquals(expResult, result);
    }

}
