package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Component
public class MealRestController {

    @Autowired
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

 public List<MealWithExceed> getAll(int userId, int caloriesPerDay){
     log.info("get all");
    return MealsUtil.getWithExceeded(service.getAll(userId), caloriesPerDay);
 }

    public List<MealWithExceed> getAllFiltered(int userId, int caloriesPerDay, LocalTime startTime, LocalTime endTime){
        log.info("get all filtered");
        return MealsUtil.getFilteredWithExceeded(service.getAll(userId), caloriesPerDay,startTime, endTime);
    }

    public Meal get(int id, int userId){
        log.info("get meal");
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id, int userId) {
        log.info("delete {}", id);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }














}