/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.controllers;

import eweather.controllers.exceptions.NonexistentEntityException;
import eweather.db.City;
import eweather.db.Weatherdata;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Trifon
 */
public class WeatherdataJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public WeatherdataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Weatherdata weatherdata) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            City cityId = weatherdata.getCityId();
            if (cityId != null) {
                cityId = em.getReference(cityId.getClass(), cityId.getId());
                weatherdata.setCityId(cityId);
            }
            em.persist(weatherdata);
            if (cityId != null) {
                cityId.getWeatherdataList().add(weatherdata);
                cityId = em.merge(cityId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Weatherdata weatherdata) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Weatherdata persistentWeatherdata = em.find(Weatherdata.class, weatherdata.getId());
            City cityIdOld = persistentWeatherdata.getCityId();
            City cityIdNew = weatherdata.getCityId();
            if (cityIdNew != null) {
                cityIdNew = em.getReference(cityIdNew.getClass(), cityIdNew.getId());
                weatherdata.setCityId(cityIdNew);
            }
            weatherdata = em.merge(weatherdata);
            if (cityIdOld != null && !cityIdOld.equals(cityIdNew)) {
                cityIdOld.getWeatherdataList().remove(weatherdata);
                cityIdOld = em.merge(cityIdOld);
            }
            if (cityIdNew != null && !cityIdNew.equals(cityIdOld)) {
                cityIdNew.getWeatherdataList().add(weatherdata);
                cityIdNew = em.merge(cityIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = weatherdata.getId();
                if (findWeatherdata(id) == null) {
                    throw new NonexistentEntityException("The weatherdata with id " + id + " no longer exists.");
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
            Weatherdata weatherdata;
            try {
                weatherdata = em.getReference(Weatherdata.class, id);
                weatherdata.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The weatherdata with id " + id + " no longer exists.", enfe);
            }
            City cityId = weatherdata.getCityId();
            if (cityId != null) {
                cityId.getWeatherdataList().remove(weatherdata);
                cityId = em.merge(cityId);
            }
            em.remove(weatherdata);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Weatherdata> findWeatherdataEntities() {
        return findWeatherdataEntities(true, -1, -1);
    }

    public List<Weatherdata> findWeatherdataEntities(int maxResults, int firstResult) {
        return findWeatherdataEntities(false, maxResults, firstResult);
    }

    private List<Weatherdata> findWeatherdataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Weatherdata.class));
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

    public Weatherdata findWeatherdata(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Weatherdata.class, id);
        } finally {
            em.close();
        }
    }

    public int getWeatherdataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Weatherdata> rt = cq.from(Weatherdata.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Weatherdata getWeatherdataByCityAndDate(Long cityId, Date date) {
        EntityManager em = getEntityManager();
        TypedQuery<Weatherdata> query
                = em.createNamedQuery("Weatherdata.findByCityAndDate",
                        Weatherdata.class);
        query.setParameter("city_id", cityId);
        query.setParameter("dt", date);
        List<Weatherdata> lst = query.getResultList();
        if (lst.isEmpty()) {
            return null;
        } else {
            return lst.get(0);
        }
    }

    public List<Weatherdata> getCityWeather(Long cityId, boolean isPrognosis,
            int maxResults) {
        EntityManager em = getEntityManager();
        TypedQuery<Weatherdata> query
                = em.createNamedQuery("Weatherdata.findCurrentCityWeather",
                        Weatherdata.class);
        query.setParameter("city_id", cityId);
        query.setParameter("isprognosis", isPrognosis);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    public List<Weatherdata> getWeatherFromTo(
            Long cityId,
            boolean isprognosis,
            Date dt_start,
            int maxRecords) {
        EntityManager em = getEntityManager();
        TypedQuery<Weatherdata> query
                = em.createNamedQuery("Weatherdata.fingWeathersFromDate",
                        Weatherdata.class);
        query.setParameter("city_id", cityId);
        query.setParameter("isprognosis", isprognosis);
        query.setParameter("dt_start", dt_start);
        query.setMaxResults(maxRecords);
        return query.getResultList();
    }

}
