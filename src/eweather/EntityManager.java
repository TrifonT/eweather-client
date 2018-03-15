/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather;

import eweather.controllers.WeatherdataJpaController;
import eweather.db.*;
import java.util.List;

/**
 *
 * @author Trifon
 */
public class EntityManager {

    public static void SaveWeather(Weatherdata weather) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController();
        cntrl.create(weather);
    }

    public static void SaveWeathers(List<Weatherdata> weathers) {
        WeatherdataJpaController cntrl = new WeatherdataJpaController();
        weathers.forEach((weather) -> {
            cntrl.create(weather);
        });        
    }

//    public static List<Weatherdata> GetWeathersByCityId(Long cityId) {
//        WeatherdataJpaController cntrl = new WeatherdataJpaController();
//        return cntrl.getWeatherdataByCityId(cityId);
//    }

}
