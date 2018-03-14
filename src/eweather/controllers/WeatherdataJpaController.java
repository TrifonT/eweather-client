/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eweather.controllers;

import eweather.controllers.exceptions.NonexistentEntityException;
import eweather.db.Weatherdata;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Trifon
 */
public class WeatherdataJpaController implements Serializable {

    public WeatherdataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Weatherdata weatherdata) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(weatherdata);
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
            weatherdata = em.merge(weatherdata);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = weatherdata.getWeatherid();
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
                weatherdata.getWeatherid();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The weatherdata with id " + id + " no longer exists.", enfe);
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
    
}
