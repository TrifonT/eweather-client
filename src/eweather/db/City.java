/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.db;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Trifon
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c")
    , @NamedQuery(name = "City.findById",
            query = "SELECT c FROM City c WHERE c.id = :id")
    , @NamedQuery(name = "City.findByCityname",
            query = "SELECT c FROM City c WHERE c.cityname = :cityname")})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Column(length = 255)
    private String cityname;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cityId")
    private List<Weatherdata> weatherdataList;

    public City() {
    }

    public City(Long id) {
        this.id = id;
    }

    public City(Long id, String name) {
        this.id = id;
        this.cityname = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public List<Weatherdata> getWeatherdataList() {
        return weatherdataList;
    }

    public void setWeatherdataList(List<Weatherdata> weatherdataList) {
        this.weatherdataList = weatherdataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work 
        // in the case the id fields are not set
        if (!(object instanceof City)) {
            return false;
        }
        City other = (City) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eweather.db.City[ id=" + id + " ]";
    }

}
