/* 
 *  � ������� ����� ����� ����� ��� �������� �������� 
 *  ��� ������ ��� ��������� �������� ���240 ���
 *  �������� ��� ���
 *  ������� ���
 *  ��������� ��������
 *  �������������� ������
 */
package eweather.gui;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Trifon
 */
public class StatTableModel extends AbstractTableModel {

    private final String[] columnNames = {
        "<html><center>����</center></html>",
        "<html><center>M������<br/>�����������</center></html>",
        "<html><center>������./���<br/>��������</center></html>",
        "<html><center>��������<br/>�����������</center></html>",
        "<html><center>������./���<br/>���������</center></html>",
        "<html><center>����<br/>�����������</center></html>"};

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
