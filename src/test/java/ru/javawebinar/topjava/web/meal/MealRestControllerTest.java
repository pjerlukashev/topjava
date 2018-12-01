package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.*;


public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
     void testGet()throws  Exception{
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
     void testDelete()throws  Exception{
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        MealTestData.assertMatch(mealService.getAll(USER_ID), List.of(MEAL6, MEAL5,MEAL4, MEAL3, MEAL2));
    }

    @Test
     void testGetAll()throws  Exception{
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6)));
    }


    @Test
    void testCreateWithLocation()throws  Exception{
        Meal  expected = new Meal( LocalDateTime.of(2016, Month.SEPTEMBER, 24, 20, 0), "dinner", 1000);

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        MealTestData.assertMatch(returned, expected);
        MealTestData.assertMatch(mealService.getAll(USER_ID), List.of(expected,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1));


    }

    @Test
    void testUpdate()throws  Exception{

        Meal updated= new Meal(MEAL1.getId(), MEAL1.getDateTime(),MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("Полдник");
        updated.setCalories(800);

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MealTestData.assertMatch(mealService.get(MEAL1_ID, USER_ID), updated );
    }

    @Test
    void testGetBetween() throws  Exception{

        mockMvc.perform(get(REST_URL +  "getbetween" + "?startdatetime=2015-05-30T09:00:00.000-05:00&enddatetime=2015-05-30T15:00:00.000-05"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1, MEAL2));
    }
}
