/* 
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.gui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Trifon
 */
public class Translator {

    private static final String[] weekDays = {"", "Κυριακή", "Δευτέρα", "Τρίτη",
        "Τετάρτη", "Πέμπτη", "Παρασκευή", "Σάββατο"};

    public static String translate(String weatherCode) {
        String result = "";
        switch (weatherCode) {
            case "clear sky":
                result = "Καθαρός ουρανός";
                break;
            case "few clouds":
                result = "Λίγα σύννεφα";
                break;
            case "broken clouds":
                result = "Διασκορπισμένα νέφη";
                break;
            case "scattered clouds":
                result = "Διάσπαρτα νέφη";
                break;
            case "shower rain":
                result = "Μπόρα";
                break;
            case "rain":
                result = "Βροχή";
                break;
            case "thunderstorm":
                result = "Καταιγίδα";
                break;
            case "snow":
                result = "Χιόνι";
                break;
            case "mist":
                result = "Ομίχλη";
                break;
            case "light rain":
                result = "Ελαφριά βροχή";
                break;
            case "moderate rain":
                result = "Μέτρια βροχή";
                break;
            case "overcast clouds":
                result = "Πυκνή συννεφιά";
                break;
            case "dust":
                result = "Σκόνη";
                break;
            default:
                result = weatherCode;
                break;
        }
        return result;
    }

    public static String translateDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat f1 = new SimpleDateFormat("dd/MM");
        SimpleDateFormat f2 = new SimpleDateFormat("HH:mm");

        return String.format("<html><center>%s %s<br>%s</center></html>",
                weekDays[dayOfWeek], f1.format(date), f2.format(date));
    }
}
