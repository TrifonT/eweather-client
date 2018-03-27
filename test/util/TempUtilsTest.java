/*
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
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
     * Έλεγχος της GetWeatherForCities πως αν κληθεί με τον κωδικό 735914 στην
     * λίστα θα περιέχεται στο αποτέλεσμα η συμβολοσειρά “Katerini”.
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

        /*Έλεγχος αν το String που λαμβάνουμε περιέχει την λέξη "Katerini"*/
        if (result.contains("Katerini")) {
            result = "Katerini";
        }
        assertEquals(expResult, result);

    }

    /**
     * Έλεγχος της GetWeatherForCities πως αν κληθεί με τρεις πόλεις στην λίστα
     * θα επιστραφεί ένα αποτέλεσμα που θα περιέχει 3 πόλεις
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

        /*Βρίσκουμε το πλήθος εγγραφών πόλεων από την ιδιότητα cnt του Json
        Object*/
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(result);
        JsonObject jo = je.getAsJsonObject();
        result = jo.get("cnt").getAsString();

        assertEquals(expResult, result);

    }

    /**
     * Έλεγχος της GetWeatherForCities πως αν κληθεί με κακοσχηματισμένο baseURL
     * θα προκληθεί πράγματι η εξαίρεση MalformedExcpetion
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
        } /* Αν δεν έχει σχηματιστεί σωστά το URL θα πρέπει να δημιουργηθεί 
        η MalformedURLException . Σε περίπτωση επιτυχίας θα έχουμε το 
        επιθυμητό αποτέλεσμα*/ catch (MalformedURLException e) {
            result = "OK";
        }

        assertEquals(expResult, result);
    }

}
