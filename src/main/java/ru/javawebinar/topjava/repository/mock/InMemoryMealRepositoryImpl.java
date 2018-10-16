package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new HashMap<>();
    private Integer counter = new Integer(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(++counter);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId ) {
            return repository.remove(id) != null;
        }
        return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal= repository.get(id);
        if(meal!= null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.values();
        return meals.stream().filter(meal -> meal.getUserId()== userId).sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }
}

