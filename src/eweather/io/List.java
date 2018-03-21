/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.io;

import java.util.ArrayList;

/**
 *
 * @author Trifon
 */
public class List {

    public Coord coord;
    public Sys sys;
    public java.util.List<Weather> weather;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public Long dt;
    public Long id;
    public String name;
    public Rain rain;
    public Snow snow;

    public float getRain() {
        if (null == rain) {
            return 0;
        } else {
            return rain.Field3H;
        }
    }

    public float getSnow() {
        if (null == snow) {
            return 0;
        } else {
            return snow.Field3h;
        }
    }

    public String getIcon() {
        if (null != weather && weather.size() > 0) {
            return weather.get(0).getIcon();
        } else {
            return "";
        }
    }

    public String getDescription() {
        if ((null != weather) && (weather.size() > 0)) {
            return weather.get(0).getDescription();
        } else {
            return "";
        }
    }

    public float getClouds() {
        if (null != clouds) {
            return clouds.all;
        } else {
            return 0;
        }
    }

    public float getTemp() {
        if (null != main) {
            return main.temp;
        } else {
            return 0;
        }
    }
}
