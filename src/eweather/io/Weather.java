/* 
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.io;

/**
 *
 * @author Trifon
 */
public class Weather {

    public Long id;
    public String main;
    public String description;
    public String icon;

    public String getIcon() {
        return (icon != null) ? icon : "";
    }

    public String getDescription() {
        return (description != null) ? description : "";
    }
}
