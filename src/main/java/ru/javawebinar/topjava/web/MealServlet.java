package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
       @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("going to meals");
         List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(MealsUtil.getMeals(),2000);
        request.setAttribute("mealList", mealWithExceeds);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
       }
}
