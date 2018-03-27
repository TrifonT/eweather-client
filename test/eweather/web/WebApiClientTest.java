/*
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.web;

import eweather.io.WeatherReport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Trifon
 */
public class WebApiClientTest {

    public WebApiClientTest() {
    }

    /**
     * Test of getWeather method, of class WebApiClient.
     */
    @Test
    public void testGetWeather() {
        System.out.println("getWeather");
        List<Long> cityIds = new ArrayList<>();
        cityIds.add(735914L);
        WebApiClient instance = new WebApiClient();
        WeatherReport result = instance.getWeather(cityIds);

        org.junit.Assert.assertNotNull(result);
        assertEquals(result.city.name, "Katerini");
    }

    /**
     * Test of getPrognosis method, of class WebApiClient.
     */
    @Test
    public void testGetPrognosis() {
        System.out.println("getPrognosis");
        WebApiClient instance = new WebApiClient();
        WeatherReport result = instance.getPrognosis(735914L);

        org.junit.Assert.assertNotNull(result);
        assertEquals(result.city.name, "Katerini");
    }

}
