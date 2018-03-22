/* 
 *  � ������� ����� ����� ����� ��� �������� �������� 
 *  ��� ������ ��� ��������� �������� ���240 ���
 *  �������� ��� ���
 *  ������� ���
 *  ��������� ��������
 *  �������������� ������
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

    private static final String[] weekDays = {"", "�������", "�������", "�����",
        "�������", "������", "���������", "�������"};

    public static String translate(String weatherCode) {
        String result = "";
        switch (weatherCode) {
            case "clear sky":
                result = "������� �������";
                break;
            case "few clouds":
                result = "���� �������";
                break;
            case "broken clouds":
                result = "�������������� ����";
                break;
            case "scattered clouds":
                result = "��������� ����";
                break;
            case "shower rain":
                result = "�����";
                break;
            case "rain":
                result = "�����";
                break;
            case "thunderstorm":
                result = "���������";
                break;
            case "snow":
                result = "�����";
                break;
            case "mist":
                result = "������";
                break;
            case "light rain":
                result = "������� �����";
                break;
            case "moderate rain":
                result = "������ �����";
                break;
            case "overcast clouds":
                result = "����� ��������";
                break;
            case "dust":
                result = "�����";
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
