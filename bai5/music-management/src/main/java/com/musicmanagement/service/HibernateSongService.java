package com.musicmanagement.service;

import com.musicmanagement.model.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HibernateSongService implements ISongService {

    private static EntityManager entityManager;
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Song> findAll() {
        String queryStr = "SELECT c FROM Song AS c";
        TypedQuery<Song> query = entityManager.createQuery(queryStr, Song.class);
        return query.getResultList();
    }

    @Override
    public Song findById(Long id) {
        String queryStr = "SELECT c FROM Song AS c WHERE c.id = :id";
        TypedQuery<Song> query = entityManager.createQuery(queryStr, Song.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void save(Song song) {
        Transaction transaction = null;
        Song origin;
        if (song.getId() == null) {
            origin = new Song();
        } else {
            origin = findById(song.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            origin.setName(song.getName());
            origin.setArtist(song.getArtist());
            origin.setCategory(song.getCategory());
            origin.setFilePath(song.getFilePath());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void remove(Long id) {
        Transaction transaction = null;
        Song song = findById(id);
        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.delete(song);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
