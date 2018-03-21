/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.controllers;

import eweather.controllers.exceptions.NonexistentEntityException;
import eweather.controllers.exceptions.PreexistingEntityException;
import eweather.db.City;
import eweather.db.Weatherdata;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Trifon
 */
public class CityJpaController implements Serializable {

    public CityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(City city) throws PreexistingEntityException, Exception {
        if (city.getWeatherdataList() == null) {
            city.setWeatherdataList(new ArrayList<Weatherdata>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Weatherdata> attachedWeatherdataList = new ArrayList<Weatherdata>();
            for (Weatherdata weatherdataListWeatherdataToAttach : city.getWeatherdataList()) {
                weatherdataListWeatherdataToAttach = em.getReference(weatherdataListWeatherdataToAttach.getClass(), weatherdataListWeatherdataToAttach.getId());
                attachedWeatherdataList.add(weatherdataListWeatherdataToAttach);
            }
            city.setWeatherdataList(attachedWeatherdataList);
            em.persist(city);
            for (Weatherdata weatherdataListWeatherdata : city.getWeatherdataList()) {
                City oldCityIdOfWeatherdataListWeatherdata = weatherdataListWeatherdata.getCityId();
                weatherdataListWeatherdata.setCityId(city);
                weatherdataListWeatherdata = em.merge(weatherdataListWeatherdata);
                if (oldCityIdOfWeatherdataListWeatherdata != null) {
                    oldCityIdOfWeatherdataListWeatherdata.getWeatherdataList().remove(weatherdataListWeatherdata);
                    oldCityIdOfWeatherdataListWeatherdata = em.merge(oldCityIdOfWeatherdataListWeatherdata);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCity(city.getId()) != null) {
                throw new PreexistingEntityException("City " + city + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(City city) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City persistentCity = em.find(City.class, city.getId());
            List<Weatherdata> weatherdataListOld = persistentCity.getWeatherdataList();
            List<Weatherdata> weatherdataListNew = city.getWeatherdataList();

            List<Weatherdata> attachedWeatherdataListNew = new ArrayList<Weatherdata>();
            for (Weatherdata weatherdataListNewWeatherdataToAttach : weatherdataListNew) {
                weatherdataListNewWeatherdataToAttach = em.getReference(weatherdataListNewWeatherdataToAttach.getClass(), weatherdataListNewWeatherdataToAttach.getId());
                attachedWeatherdataListNew.add(weatherdataListNewWeatherdataToAttach);
            }
            weatherdataListNew = attachedWeatherdataListNew;
            city.setWeatherdataList(weatherdataListNew);
            city = em.merge(city);
            for (Weatherdata weatherdataListOldWeatherdata : weatherdataListOld) {
                if (!weatherdataListNew.contains(weatherdataListOldWeatherdata)) {
                    weatherdataListOldWeatherdata.setCityId(null);
                    weatherdataListOldWeatherdata = em.merge(weatherdataListOldWeatherdata);
                }
            }
            for (Weatherdata weatherdataListNewWeatherdata : weatherdataListNew) {
                if (!weatherdataListOld.contains(weatherdataListNewWeatherdata)) {
                    City oldCityIdOfWeatherdataListNewWeatherdata = weatherdataListNewWeatherdata.getCityId();
                    weatherdataListNewWeatherdata.setCityId(city);
                    weatherdataListNewWeatherdata = em.merge(weatherdataListNewWeatherdata);
                    if (oldCityIdOfWeatherdataListNewWeatherdata != null && !oldCityIdOfWeatherdataListNewWeatherdata.equals(city)) {
                        oldCityIdOfWeatherdataListNewWeatherdata.getWeatherdataList().remove(weatherdataListNewWeatherdata);
                        oldCityIdOfWeatherdataListNewWeatherdata = em.merge(oldCityIdOfWeatherdataListNewWeatherdata);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = city.getId();
                if (findCity(id) == null) {
                    throw new NonexistentEntityException("The city with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City city;
            try {
                city = em.getReference(City.class, id);
                city.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The city with id " + id + " no longer exists.", enfe);
            }
            List<Weatherdata> weatherdataList = city.getWeatherdataList();
            for (Weatherdata weatherdataListWeatherdata : weatherdataList) {
                weatherdataListWeatherdata.setCityId(null);
                weatherdataListWeatherdata = em.merge(weatherdataListWeatherdata);
            }
            em.remove(city);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<City> findCityEntities() {
        return findCityEntities(true, -1, -1);
    }

    public List<City> findCityEntities(int maxResults, int firstResult) {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(City.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public City findCity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(City.class, id);
        } finally {
            em.close();
        }
    }

    public int getCityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<City> rt = cq.from(City.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
