/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Trifon
 */
@Entity
@Table(name = "WEATHERDATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weatherdata.findAll", query = "SELECT w FROM Weatherdata w")
    , @NamedQuery(name = "Weatherdata.findByWeatherid", query = "SELECT w FROM Weatherdata w WHERE w.weatherid = :weatherid")
    , @NamedQuery(name = "Weatherdata.findByCityid", query = "SELECT w FROM Weatherdata w WHERE w.cityid = :cityid")
    , @NamedQuery(name = "Weatherdata.findByDt", query = "SELECT w FROM Weatherdata w WHERE w.dt = :dt")
    , @NamedQuery(name = "Weatherdata.findByTemp", query = "SELECT w FROM Weatherdata w WHERE w.temp = :temp")
    , @NamedQuery(name = "Weatherdata.findByDescription", query = "SELECT w FROM Weatherdata w WHERE w.description = :description")
    , @NamedQuery(name = "Weatherdata.findByClouds", query = "SELECT w FROM Weatherdata w WHERE w.clouds = :clouds")
    , @NamedQuery(name = "Weatherdata.findByWindspeed", query = "SELECT w FROM Weatherdata w WHERE w.windspeed = :windspeed")
    , @NamedQuery(name = "Weatherdata.findByRain", query = "SELECT w FROM Weatherdata w WHERE w.rain = :rain")
    , @NamedQuery(name = "Weatherdata.findBySnow", query = "SELECT w FROM Weatherdata w WHERE w.snow = :snow")})
public class Weatherdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "WEATHERID")
    private Integer weatherid;
    @Column(name = "CITYID")
    private Integer cityid;
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TEMP")
    private Float temp;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CLOUDS")
    private Float clouds;
    @Column(name = "WINDSPEED")
    private Float windspeed;
    @Column(name = "RAIN")
    private Float rain;
    @Column(name = "SNOW")
    private Float snow;

    public Weatherdata() {
    }

    public Weatherdata(Integer weatherid) {
        this.weatherid = weatherid;
    }

    public Integer getWeatherid() {
        return weatherid;
    }

    public void setWeatherid(Integer weatherid) {
        this.weatherid = weatherid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getClouds() {
        return clouds;
    }

    public void setClouds(Float clouds) {
        this.clouds = clouds;
    }

    public Float getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Float windspeed) {
        this.windspeed = windspeed;
    }

    public Float getRain() {
        return rain;
    }

    public void setRain(Float rain) {
        this.rain = rain;
    }

    public Float getSnow() {
        return snow;
    }

    public void setSnow(Float snow) {
        this.snow = snow;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherid != null ? weatherid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weatherdata)) {
            return false;
        }
        Weatherdata other = (Weatherdata) object;
        if ((this.weatherid == null && other.weatherid != null) || (this.weatherid != null && !this.weatherid.equals(other.weatherid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eweather.db.Weatherdata[ weatherid=" + weatherid + " ]";
    }
    
}
