package ru.skdvp.app.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skdvp.app.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@Primary
public class UserDaoHibernateImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> showAllUsers() {
        return entityManager.createQuery(
                "select u from User u", User.class).getResultList();
    }


    @Override
    public User showUser(long id) {
        TypedQuery<User> typedQuery = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);

        typedQuery.setParameter("id", id);
        return typedQuery.getResultList().stream().findFirst().orElse(null);
    }


    @Transactional
    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }


    @Transactional
    @Override
    public void updateUser(long id, User updateUser) {

        TypedQuery<User> userShowQuery = entityManager.createQuery(
                "select u from User u where u.id = :id", User.class);
        userShowQuery.setParameter("id", id).getSingleResult();

        User userToBeUpdated = userShowQuery.getSingleResult();
        userToBeUpdated.setName(updateUser.getName());
        userToBeUpdated.setSurname(updateUser.getSurname());
        userToBeUpdated.setAge(updateUser.getAge());

    }


    @Transactional
    @Override
    public void removeUserById(long id) {
        TypedQuery<User> userDeleteQuery = entityManager
                .createQuery("select u from User u where u.id = :id", User.class);
        userDeleteQuery.setParameter("id", id).getSingleResult();

        entityManager.remove(userDeleteQuery.getSingleResult());
    }
}
