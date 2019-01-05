package ru.javawebinar.topjava.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;


    @Component
    public class UserFormValidator implements Validator {

    @Autowired
    UserService service;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "","Name is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "","Email is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","", "Password is required!");

        if(user.getName().length()<2 || user.getName().length()>100 ){errors.rejectValue("name", "","User name has inappropriate length!");}
        if(user.getEmail().length()>100 || !user.getEmail().contains("@") || !user.getEmail().contains(".")  ){errors.rejectValue("email", "","Invalid Email format!");}
        if(user.getPassword().length()>100 || user.getPassword().length()< 5  ){errors.rejectValue("password","", "Password has inappropriate length!");}

        if (!service.checkDuplicatingWithEmail(user)){errors.rejectValue("email", "","User with this email already exists!");}

        if(user.getCaloriesPerDay()<10 || user.getCaloriesPerDay()>10000 ){errors.rejectValue("caloriesPerDay", "","Inappropriate value!");}
    }
}
