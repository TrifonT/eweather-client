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
public class List
{
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
    public float rain;
    public float snow;
}