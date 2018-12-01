package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users", hasSize(2)))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(START_SEQ)),
                                hasProperty("name", is(USER.getName()))
                        )
                )));
    }

    @Test
    void testMeals() throws Exception{

     mockMvc.perform(get("/meals"))
             .andDo((print()))
             .andExpect(status().isOk())
             .andExpect(view().name("meals"))
             .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"))
             .andExpect(model().attribute("meals", hasSize(6)))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+2)),
                         hasProperty("dateTime",  is(of(2015, Month.MAY, 30, 10, 0)))))))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+3)),
                         hasProperty("dateTime",  is(of(2015, Month.MAY, 30, 13, 0)))))))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+4)),
                         hasProperty("dateTime",  is(of(2015, Month.MAY, 30, 20, 0)))))))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+5)),
                         hasProperty("dateTime",  is(of(2015, Month.MAY, 31, 10, 0)))))))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+6)),
                         hasProperty("dateTime",  is(of(2015, Month.MAY, 31, 13, 0)))))))
             .andExpect(model().attribute("meals" , hasItem(
                 allOf(
                         hasProperty("id", is(START_SEQ+7)),
                         hasProperty("dateTime",  is( of(2015, Month.MAY, 31, 20, 0)))))));
    }
}