/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
