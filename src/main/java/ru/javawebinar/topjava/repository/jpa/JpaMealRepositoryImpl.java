package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal oldMeal = em.getReference(Meal.class, meal.getId());
            if(oldMeal.getUser().getId()==userId)
            return em.merge(meal);
        }
        return  null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userid");
        return query.setParameter("id", id).setParameter("userid", userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        return meal.getUser().getId() == userId? meal: null ;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter("userid", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return em.createNamedQuery(Meal.BETWEEN_DATES, Meal.class).setParameter("userid", userId).setParameter("startdatetime",startDate).setParameter("enddatetime",endDate).getResultList();
    }
}