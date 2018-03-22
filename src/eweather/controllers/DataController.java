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
     * Εισάγει τις πόλεις στην database αφού κάνει έλεγχο για την κάθε μια άν
     * είναι ήδη καταωρημένη ή όχι
     */
    public static void initCities() {
        try {
            ArrayList<City> cities = new ArrayList<>();
            cities.add(new City(264371L, "Αθήνα"));
            cities.add(new City(734077L, "Θεσσαλονίκη"));
            cities.add(new City(8133690L, "Πάτρα"));
            cities.add(new City(8133786L, "Λάρισα"));
            cities.add(new City(261743L, "Ηράκλειο"));
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
     * Καταχωρεί τον τρέχοντα καιρό που υπάρχει στο αντικείμενο 
     * Weatherdata σε μια συγκεκριμένη πόλη με ID = cityId
     *
     * @param cityId ο κωδικός της πόλης
     * @param data το αντικείμενο που περιέχει τον καιτό της πόλης
     */
    public static void updateCityWeatherdata(Long cityId, Weatherdata data) {
        EntityManagerFactory emf = getEMF();
        CityJpaController c_cntrl = new CityJpaController(emf);
        // Γίνεται αναζήτηση για την πόλη
        City city = c_cntrl.findCity(cityId);
        // Αν η πόλη δεν υπάρχει δεν μπορούμε να καταχωρήσουμε τον καιρό
        if (city != null) {
            data.setCityId(city);
            // Πρέπει να κάνουμε έλεγχο για το αν έχει υπάρξει άλλη εγγραφή
            // για την ίδια ώρα για την ίδια πόλη
            WeatherdataJpaController w_cntrl
                    = new WeatherdataJpaController(emf);
            Weatherdata old_wd
                    = w_cntrl.getWeatherdataByCityAndDate(cityId, data.getDt());
            // αν δεν υπάρχει προηγούμενη εγγραφή, τότε καταχώρησε
            if ((old_wd == null)
                    || old_wd.getIsPrognosis() != data.getIsPrognosis()) {
                // Δημιουργούμε την νέα εγγραφή
                w_cntrl.create(data);
            } else {
                // αν έχει προυπάρξει άλλη εγγραφή, τότε ενημέρωσέ την παλιά
                // πρόγνωση ή μετέτρεψέ την σε τρέχοντα καιρό
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
     * Μετατρέπει ένα αντικείμενο WeatherReport που προήλθε από τον 
     * WebApiClient σε ένα αντικείμενο WeatherData που αφορά την 
     * βάση δεδομένων. Έπειτα αυτό το αντικείμενο στέλνεται προς ενημέρωση.
     *
     * @param report Το αντικείμενο που περιέχει τα στοιχεία για ενημέρωση
     * @param cityid Ορίζει την πόλη που αφορά η πρόγνωση
     * @param isPrognosis ορίζει αν πρόκειται για πρόβλεψη ή για τρέχοντα καιρό
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
     * Ενημερώνει τον τρέχοντα καιρό για όλες τις πόλεις
     */
    public static void updateCurrentWeather() {
        // Παίρνουμε τις πόλεις από την database        
        List<City> cities = getCities();
        // Παίρνουμε τα ID τους
        List<Long> cityIds = getIdsFromCities(cities);
        // Καλούμε τον‘WebApiClient για να πάρει τον τρέχοντα καιρό
        WebApiClient client = new WebApiClient();
        WeatherReport report = client.getWeather(cityIds);
        // Ενημερώνουμε την database
        updateWeatherReport(report, -1L, false);
    }

    /**
     * Eνημερώνει τις προγνώσεις για όλες τις πόλεις
     */
    public static void updatePrognosisWeather() {

        // Παίρνουμε τις πόλεις από την database        
        List<Long> cityIds = getCitiesIds();
        WebApiClient client = new WebApiClient();
        cityIds.forEach((cityid) -> {
            WeatherReport report = client.getPrognosis(cityid);
            updateWeatherReport(report, cityid, true);
        });
    }

    /**
     * Επιστρέφει τον καιρό για τις πόλεις με τα ID που περιέχονται 
     * στο όρισμα της συνάρτησης.
     *
     * @param cityIds Λίστα με ID
     * @return Λίστα με αντικείμενα Weatherdata
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
     * Επιστρέφει όλα τα δεδομένα καιρού που είναι αποθηκευμένα 
     * στην βάση δεδομένων για μια συγκεκριμένη πόλη
     *
     * @param cityId Το ID της πόλης
     * @return Λίστα με δεδομένα καιρού
     */
    public static List<Weatherdata> getAllWeatherdata(Long cityId) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getCityWeather(cityId, false, Integer.MAX_VALUE);
    }

    /**
     * Επιστρέφει τις εγγραφές πρόγνωσης που αφορούν μια πόλη για το
     * διάστημα μιας ημέρας.
     * 
     * @param cityId Το ID της πόλης
     * @param dt_start Ημερομηνία που αφορά την αρχή των προβλέψεων
     * @return Λίστα με αντικείμενα Weatherdata
     */
    public static List<Weatherdata> getPrognosis1Day(Long cityId, Date dt_start) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController(getEMF());
        return cntrl.getWeatherFromTo(cityId, true, dt_start, 8);
    }

    /**
     * Επιστρέφει τις εγγραφές πρόγνωσης που αφορούν μια πόλη για το
     * διάστημα 5 ημερών
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
