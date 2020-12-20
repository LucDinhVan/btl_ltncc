package com.ato.dao.common;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class HibernateDAO<E , K extends Serializable> {
    @Autowired
    private SessionFactory sessionFactory;

    protected Class<? extends E> daoType;

    public HibernateDAO() {
        daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session currentSession() {
        return sessionFactory.openSession();
    }

    public Long insert(E entity) {
        Long id = (Long) currentSession().save(entity);
        currentSession().close();
        return id;
    }
    public void update(E entity) {
        org.hibernate.Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    public void insertOrUpdate(E entity) {
        org.hibernate.Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            currentSession().saveOrUpdate(entity);
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    public void delete(E entity) {
        org.hibernate.Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
        catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    public E find(K key) {
        return (E) currentSession().get(daoType, key);
    }

    public E findByFiled(String property, Object value) {
        return (E) findCriteria(daoType, property, value).uniqueResult();
    }

    public List<E> findAll() {
        return currentSession().createCriteria(daoType).list();
    }

    public List<E> find(String property, Object value) {
        return find(daoType, property, value);
    }

    public List<E> find(String[] arrProperty, Object[] arrValue) {
        return find(daoType, arrProperty, arrValue);
    }

    public <T> List<T> find(Class<? extends T> beanClass, String property, Object value) {
        return findCriteria(beanClass, property, value).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<? extends T> beanClass, String[] arrProperty, Object[] arrValue) {
        Criteria cri = currentSession().createCriteria(beanClass);
        for (int i = 0; i < arrProperty.length; i++) {
            cri.add(Restrictions.eq(arrProperty[i], arrValue[i]));
        }
        return cri.list();
    }

    private Criteria findCriteria(Class<?> beanClass, String property, Object value) {
        Criteria cri = currentSession().createCriteria(beanClass);
        cri.add(Restrictions.eq(property, value));
        return cri;
    }

    public boolean isExists(String property, Object value) {
        return isExists(daoType, property, value);
    }

    public boolean isExists(Class<?> beanClass, String property, Object value) {
        Criteria cri = findCriteria(beanClass, property, value);
        cri.setMaxResults(1);
        List<?> lst = cri.list();
        return !lst.isEmpty();
    }
}