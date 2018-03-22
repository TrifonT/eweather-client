/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.gui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 *
 * @author Trifon
 */
public class StatTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "<html><center>Πόλη</center></html>",
        "<html><center>Mέγιστη<br/>Θερμοκρασία</center></html>",
        "<html><center>Ημερομ./ώρα<br/>Μέγιστης</center></html>",
        "<html><center>Ελάχιστη<br/>Θερμοκρασία</center></html>",
        "<html><center>Ημερομ./ώρα<br/>Ελάχιστης</center></html>",
        "<html><center>Μέση<br/>Θερμοκρασία</center></html>"};

    List<StatData> statData;

    public StatTableModel(List<StatData> statData) {
        this.statData = statData;
    }

    @Override
    public int getRowCount() {
        return this.statData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int index) {
        return columnNames[index];
    }

    @Override
    public Object getValueAt(int row, int col) {
        StatData data = statData.get(row);
        switch (col) {
            case 0:
                return data.CityName;
            case 1:
                return String.format("%.1f", data.MaxTemp);
            case 2:
                return Translator.translateDate(data.MaxDate);
            case 3:
                return String.format("%.1f", data.MinTemp);
            case 4:
                return Translator.translateDate(data.MinDate);
            case 5:
                return String.format("%.1f", data.AveTemp);
            default:
                return "";
        }
    }
}
