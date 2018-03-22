package eweather.controllers;

import eweather.db.City;
import eweather.db.Weatherdata;
import eweather.io.WeatherReport;
import eweather.web.WebApiClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Trifon
 */
public class DataController {

    private static final Logger LOGGER
            = Logger.getLogger(DataController.class.getName());

    /**
     *
     * @return
     */
    public static EntityManagerFactory getEMF() {
        return Persistence.createEntityManagerFactory("eweatherPU");
    }

    /**
     *
     * @return
     */
    public static List<City> getCities() {
        CityJpaController cntrl = new CityJpaController(getEMF());
        return cntrl.findCityEntities();
    }

    /**
     *
     * @param cities
     * @return
     */
    public static List<Long> getIdsFromCities(List<City> cities) {
        ArrayList<Long> ids = new ArrayList<>();
        cities.forEach(city -> {
            ids.add(city.getId());
        });
        return ids;
    }

    /**
     *
     * @return
     */
    private static List<Long> getCitiesIds() {
        return getIdsFromCities(getCities());
    }

    /**
     * ������� ��� ������ ���� database ���� ����� ������ ��� ��� ���� ��� ��
     * ����� ��� ����������� � ���
     */
    public static void initCities() {
        try {
            ArrayList<City> cities = new ArrayList<>();
            cities.add(new City(264371L, "�����"));
            cities.add(new City(734077L, "�����������"));
            cities.add(new City(8133690L, "�����"));
            cities.add(new City(8133786L, "������"));
            cities.add(new City(261743L, "��������"));
            CityJpaController cntrl = new CityJpaController(getEMF());
            for (City city : cities) {
                if (cntrl.findCity(city.getId()) == null) {
                    cntrl.create(city);
                }
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * ��������� ��� �������� ����� ��� ������� ��� ����������� 
     * Weatherdata �� ��� ������������ ���� �� ID = cityId
     *
     * @param cityId � ������� ��� �����
     * @param data �� ����������� ��� �������� ��� ����� ��� �����
     */
    public static void updateCityWeatherdata(Long cityId, Weatherdata data) {
        EntityManagerFactory emf = getEMF();
        CityJpaController c_cntrl = new CityJpaController(emf);
        // ������� ��������� ��� ��� ����
        City city = c_cntrl.findCity(cityId);
        // �� � ���� ��� ������� ��� �������� �� ������������� ��� �����
        if (city != null) {
            data.setCityId(city);
            // ������ �� ������� ������ ��� �� �� ���� ������� ���� �������
            // ��� ��� ���� ��� ��� ��� ���� ����
            WeatherdataJpaController w_cntrl
                    = new WeatherdataJpaController(emf);
            Weatherdata old_wd
                    = w_cntrl.getWeatherdataByCityAndDate(cityId, data.getDt());
            // �� ��� ������� ����������� �������, ���� ����������
            if ((old_wd == null)
                    || old_wd.getIsPrognosis() != data.getIsPrognosis()) {
                // ������������ ��� ��� �������
                w_cntrl.create(data);
            } else {
                // �� ���� ���������� ���� �������, ���� ��������� ��� �����
                // �������� � ��������� ��� �� �������� �����
                old_wd.setDt(data.getDt());
                old_wd.setDescription(data.getDescription());
                old_wd.setClouds(data.getClouds());
                old_wd.setRain(data.getRain());
                old_wd.setSnow(data.getSnow());
                old_wd.setTemp(data.getTemp());
                old_wd.setWindspeed(data.getWindspeed());
                old_wd.setIcon(data.getIcon());
                old_wd.setIsPrognosis(data.getIsPrognosis());

                try {
                    w_cntrl.edit(old_wd);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * ���������� ��� ����������� WeatherReport ��� ������� ��� ��� 
     * WebApiClient �� ��� ����������� WeatherData ��� ����� ��� 
     * ���� ���������. ������ ���� �� ����������� ��������� ���� ���������.
     *
     * @param report �� ����������� ��� �������� �� �������� ��� ���������
     * @param cityid ������ ��� ���� ��� ����� � ��������
     * @param isPrognosis ������ �� ��������� ��� �������� � ��� �������� �����
     */
    public static void updateWeatherReport(WeatherReport report, Long cityid,
            boolean isPrognosis) {

        report.list.forEach((eweather.io.List apidata) -> {
            long c_id = (isPrognosis) ? cityid : apidata.id;
            Weatherdata weatherdata = new Weatherdata();
            Date dt = new Date(apidata.dt * 1000);
            weatherdata.setDt(dt);
            weatherdata.setDescription(apidata.getDescription());
            weatherdata.setClouds(apidata.getClouds());
            weatherdata.setRain(apidata.getRain());
            weatherdata.setSnow(apidata.getSnow());
            weatherdata.setTemp(apidata.main.temp);
            weatherdata.setWindspeed(apidata.wind.speed);
            weatherdata.setIcon(apidata.getIcon());
            weatherdata.setIsPrognosis(isPrognosis);

            updateCityWeatherdata(c_id, weatherdata);
        });
    }

    /**
     * ���������� ��� �������� ����� ��� ���� ��� ������
     */
    public static void updateCurrentWeather() {
        // ��������� ��� ������ ��� ��� database        
        List<City> cities = getCities();
        // ��������� �� ID ����
        List<Long> cityIds = getIdsFromCities(cities);
        // ������� ���WebApiClient ��� �� ����� ��� �������� �����
        WebApiClient client = new WebApiClient();
        WeatherReport report = client.getWeather(cityIds);
        // ������������ ��� database
        updateWeatherReport(report, -1L, false);
    }

    /**
     * E��������� ��� ���������� ��� ���� ��� ������
     */
    public static void updatePrognosisWeather() {

        // ��������� ��� ������ ��� ��� database        
        List<Long> cityIds = getCitiesIds();
        WebApiClient client = new WebApiClient();
        cityIds.forEach((cityid) -> {
            WeatherReport report = client.getPrognosis(cityid);
            updateWeatherReport(report, cityid, true);
        });
    }

    /**
     * ���������� ��� ����� ��� ��� ������ �� �� ID ��� ����������� 
     * ��� ������ ��� ����������.
     *
     * @param cityIds ����� �� ID
     * @return ����� �� ����������� Weatherdata
     */
    public static List<Weatherdata> getWeatherdata(List<Long> cityIds) {

        List<Weatherdata> result = new ArrayList<>();
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        cityIds.forEach(city_id -> {
            List<Weatherdata> r_city = cntrl.getCityWeather(city_id, false, 1);
            if (r_city != null) {
                result.addAll(r_city);
            }
        });
        return result;
    }

    /**
     * ���������� ��� �� �������� ������ ��� ����� ������������ 
     * ���� ���� ��������� ��� ��� ������������ ����
     *
     * @param cityId �� ID ��� �����
     * @return ����� �� �������� ������
     */
    public static List<Weatherdata> getAllWeatherdata(Long cityId) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getCityWeather(cityId, false, Integer.MAX_VALUE);
    }

    /**
     * ���������� ��� �������� ��������� ��� ������� ��� ���� ��� ��
     * �������� ���� ������.
     * 
     * @param cityId �� ID ��� �����
     * @param dt_start ���������� ��� ����� ��� ���� ��� ����������
     * @return ����� �� ����������� Weatherdata
     */
    public static List<Weatherdata> getPrognosis1Day(Long cityId, Date dt_start) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getWeatherFromTo(cityId, true, dt_start, 8);
    }

    /**
     * ���������� ��� �������� ��������� ��� ������� ��� ���� ��� ��
     * �������� 5 ������
     * 
     * @param cityId
     * @param dt_start
     * @return 
     */
    public static List<Weatherdata> getPrognosis5Day(Long cityId, Date dt_start) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getWeatherFromTo(cityId, true, dt_start, 40);
    }

    /**
     *
     * @param cityId
     * @param days
     * @return
     */
    public static List<Weatherdata> getPrognosisdata(Long cityId, int days) {
        int count = days * 8;
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getCityWeather(cityId, true, count);
    }

}
