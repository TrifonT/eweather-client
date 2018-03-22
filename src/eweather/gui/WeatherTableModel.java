/* 
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import eweather.db.Weatherdata;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Trifon
 */
public class WeatherTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "<html><center>Πόλη</center></html>",
        "<html><center>Ημερομηνία,<br/>ώρα</center></html>",
        "<html><center></center></html>",
        "<html><center>Περιγραφή<br/>καιρού</center></html>",
        "<html><center>Θερμοκρ.</center></html>",
        "<html><center>Σύννεφα</center></html>",
        "<html><center>Ταχύτητα<br/>ανέμου</center></html>",
        "<html><center>Βροχή<br/>3ωρ</center></html>",
        "<htmL><center>Χιόνι<br/>3ώρ</center></html>"};

    private List<Weatherdata> data;

    WeatherTableModel() {
        this.data = new ArrayList<Weatherdata>();
    }

    WeatherTableModel(List<Weatherdata> data) {
        this.data = data;
    }

    public List<Weatherdata> getData() {
        return data;
    }

    public void setData(List<Weatherdata> data) {
        this.data = data;
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Class getColumnClass(int index) {
        switch (index) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return ImageIcon.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return String.class;
            default:
                return getValueAt(0, index).getClass();
        }

    }

    @Override
    public Object getValueAt(int row, int col) {
        Weatherdata weather = data.get(row);
        switch (col) {
            case 0:
                return weather.getCityId().getCityname();
            case 1:
                return Translator.translateDate(weather.getDt());
            case 2:
                return getIconFromIconName(weather.getIcon());
            case 3:
                return Translator.translate(weather.getDescription());
            case 4:
                return String.format("%.1f", weather.getTemp());
            case 5:
                return String.format("%.0f%%", weather.getClouds());
            case 6:
                return String.format("%.2f", weather.getWindspeed());
            case 7:
                return String.format("%.1f", weather.getRain());
            case 8:
                return String.format("%.1f", weather.getSnow());
            default:
                return "";
        }
    }

    private ImageIcon getIconFromIconName(String iconName) {
        ImageIcon result = new ImageIcon(getClass().getClassLoader().getResource("resources/icons/" + iconName + ".png"));
        return result;
    }

}
