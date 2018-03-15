/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather;


//import eweather.db.*;

import eweather.io.*;
import eweather.db.*;
import java.util.ArrayList;
//import eweather.controllers.*;


/**
 *
 * @author Trifon
 */
public class Eweather {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args)  throws Exception
    {
      
        
        
        
        
        
        
        
        ApiClient client = new ApiClient();
        
        ArrayList<Integer> cities = new ArrayList<>();
        cities.add(734077);
        cities.add(8133690);
        cities.add(264371);     
        
         ArrayList<Weatherdata> cityWeathers = client.GetWeather(cities);
        
         for(int i = 0;i<cityWeathers.size();i++)
         {
             System.out.printf("City with id = %s \n", cityWeathers.get(i).getCityid());
             System.out.printf("Weather descr = %s \n", cityWeathers.get(i).getDescription());
             System.out.println("-------------------------------------");
         }
         
         
        
        
    }
    
}
