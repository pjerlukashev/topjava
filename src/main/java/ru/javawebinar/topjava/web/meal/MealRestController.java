package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;


@Component
public class MealRestController {

    @Autowired
    private MealService service;

    protected final Logger log = LoggerFactory.getLogger(getClass());

 public List<MealWithExceed> getAll(){
     log.info("get all");
    return MealsUtil.getWithExceeded(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
 }

    public List<MealWithExceed> getAllFiltered(LocalTime startTime, LocalTime endTime){
        log.info("get all filtered");
        return MealsUtil.getFilteredWithExceeded(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(),startTime, endTime);
    }

    public Meal get(int id) throws NotFoundException {
        log.info("get meal");
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) throws NotFoundException {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(SecurityUtil.authUserId());
        return service.create(meal);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) throws NotFoundException {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        meal.setUserId(SecurityUtil.authUserId());
        service.update(meal);
    }














}