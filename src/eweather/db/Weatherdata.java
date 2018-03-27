/* 
 *  Ο κώδικας αυτός είναι μέρος της ομαδικής εργασίας 
 *  στο πλαίσο της θεματικής ενότητας ΠΛΗ240 των
 *  φοιτητών του ΕΑΠ
 *  Παυλίδη Άρη
 *  Ταφραλίδη Νικόλαου
 *  Τριανταφυλλίδη Τρύφων
 */
package eweather.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Trifon
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Weatherdata.findAll",
            query = "SELECT w FROM Weatherdata w")
    , @NamedQuery(name = "Weatherdata.findById",
            query = "SELECT w FROM Weatherdata w WHERE w.id = :id")

    // Δημιουργούμε αυτό το NamedQuery για να μπορούμε να ανακτήσουμε ένα 
    // αντικείμενο Weatherdata με κωδικό και ημερομηνία και με το εάν 
    // είναι πρόβλεψη ή τρέχων καιρός
    , @NamedQuery(name = "Weatherdata.findByCityAndDate",
            query = "SELECT w FROM Weatherdata w WHERE w.cityId.id = :city_id"
            + " AND w.dt = :dt")

    // Επιστρέφει την πιο πρόσφατη ενημέρωση για τον τρέχοντα καιρό της 
    // πόλης που ορίζεται από την παράμετρο city_id
    , @NamedQuery(name = "Weatherdata.findCurrentCityWeather",
            query = "SELECT w FROM Weatherdata w WHERE w.cityId.id = :city_id "
            + "AND w.isprognosis = :isprognosis ORDER BY DT DESC")

    // Επιστρέφει τον τρέχοντα καιρό ή τις προγνώσεις καιρού για μια 
    //  συγκεκριμένη πόλη από το διάστημα που ορίζεται από την μεταβλητή 
    // dt_start
    , @NamedQuery(name = "Weatherdata.fingWeathersFromDate",
            query = "SELECT w FROM Weatherdata w WHERE w.cityId.id = :city_id "
            + "AND w.isprognosis = :isprognosis AND w.dt >= :dt_start "
            + "ORDER BY w.dt ASC")

    , @NamedQuery(name = "Weatherdata.findByClouds",
            query = "SELECT w FROM Weatherdata w WHERE w.clouds = :clouds")
    , @NamedQuery(name = "Weatherdata.findByDescription",
            query = "SELECT w FROM Weatherdata w WHERE w.description = :description")
    , @NamedQuery(name = "Weatherdata.findByDt",
            query = "SELECT w FROM Weatherdata w WHERE w.dt = :dt")
    , @NamedQuery(name = "Weatherdata.findByRain",
            query = "SELECT w FROM Weatherdata w WHERE w.rain = :rain")
    , @NamedQuery(name = "Weatherdata.findBySnow",
            query = "SELECT w FROM Weatherdata w WHERE w.snow = :snow")
    , @NamedQuery(name = "Weatherdata.findByTemp",
            query = "SELECT w FROM Weatherdata w WHERE w.temp = :temp")
    , @NamedQuery(name = "Weatherdata.findByWindspeed",
            query = "SELECT w FROM Weatherdata w WHERE w.windspeed = :windspeed")})
public class Weatherdata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Column(length = 150)
    private String description;
    private Float clouds;
    private Float rain;
    private Float snow;
    private Float temp;
    private Float windspeed;
    @Column(length = 10)
    private String icon;
    private boolean isprognosis;

    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    @ManyToOne
    private City cityId;

    public Weatherdata() {
    }

    public Weatherdata(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getClouds() {
        return clouds;
    }

    public void setClouds(Float clouds) {
        this.clouds = clouds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
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

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(Float windspeed) {
        this.windspeed = windspeed;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean getIsPrognosis() {
        return isprognosis;
    }

    public void setIsPrognosis(boolean isPrognosis) {
        this.isprognosis = isPrognosis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in 
        // the case the id fields are not set
        if (!(object instanceof Weatherdata)) {
            return false;
        }
        Weatherdata other = (Weatherdata) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eweather.db.Weatherdata[ id=" + id + " ]";
    }

}
