package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/rpg");
        sessionFactory = new Configuration()
                .addAnnotatedClass(Player.class)
                .addProperties(properties)
                .buildSessionFactory();
    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        try(Session session = sessionFactory.openSession()) {
            String SQL = "SELECT * FROM player LIMIT :limit OFFSET :offset";

            NativeQuery<Player> query = session.createNativeQuery(SQL, Player.class);
            query.setParameter("limit", pageSize);
            query.setParameter("offset", (pageSize * pageNumber));

            return query.list();
        }
    }

    @Override
    public int getAllCount() {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createNamedQuery("Player_GetAllCount", Long.class).getSingleResult();
            return Math.toIntExact(count);
        }
    }

    @Override
    public Player save(Player player) {
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            session.persist(player);

            session.getTransaction().commit();
            return player;
        }
    }

    @Override
    public Player update(Player player) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            session.update(player);

            session.getTransaction().commit();
            return player;
        }
    }

    @Override
    public Optional<Player> findById(long id) {
        try(Session session = sessionFactory.openSession()) {
            Player player = session.find(Player.class, id);
            return Optional.of(player);
        }
    }

    @Override
    public void delete(Player player) {
        try(Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();

            Player playerToRemove = session.find(Player.class, player.getId());
            session.remove(playerToRemove);

            session.getTransaction().commit();
        }
    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }
}