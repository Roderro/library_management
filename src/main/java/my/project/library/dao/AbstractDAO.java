package my.project.library.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T> implements DAO<T> {
    private final Class<T> clazz;
    @Autowired
    protected SessionFactory sessionFactory;

    public AbstractDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Optional<List<T>> getAll() throws HibernateException {
        List<T> allItem;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<T> query = session.createQuery("from " + clazz.getName(), clazz);
            session.getTransaction().commit();
            allItem = query.list();
        }
        return Optional.ofNullable(allItem);
    }

    @Override
    public Optional<T> getById(long key) throws HibernateException {
        T foundItem = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            foundItem = session.get(clazz, key);
            session.getTransaction().commit();
        }
        return Optional.ofNullable(foundItem);
    }

    @Override
    public void add(T item) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(T item) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public Optional<T> update(T item) throws HibernateException {
        T updateItem = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            updateItem = session.merge(item);
            session.getTransaction().commit();
        }
        return Optional.ofNullable(updateItem);
    }

    @Override
    public void deleteByID(long key) throws HibernateException {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<T> item = Optional.ofNullable(session.get(clazz, key));
            item.ifPresent(session::remove);
            session.getTransaction().commit();
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }
}