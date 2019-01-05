package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class UserProfileToValidator implements Validator {

    @Autowired
    UserService service;


    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserTo userTo = (UserTo) o;
        if (!service.checkDuplicatingWithEmailRegisteredUser(userTo)){errors.rejectValue("email", "", "User with this email already exists!");}

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","", "NotEmpty.userForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "","Email is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "","Password is required!");

        if(userTo.getEmail().length()>100 || !userTo.getEmail().contains("@") || !userTo.getEmail().contains(".")  ){errors.rejectValue("email", "","Invalid Email format!");}
        if(userTo.getName().length()<2 || userTo.getName().length()>100 ){errors.rejectValue("name", "", "User name has inappropriate length!");}if(userTo.getEmail().length()>100 || !userTo.getEmail().contains("@") || !userTo.getEmail().contains(".")  ){errors.rejectValue("email", "Invalid Email format!");}
        if(userTo.getPassword().length()>100 || userTo.getPassword().length()< 5  ){errors.rejectValue("password", "", "Password has inappropriate length!");}
        if(userTo.getCaloriesPerDay()<10 || userTo.getCaloriesPerDay()>10000 ){errors.rejectValue("caloriesPerDay", "", "Inappropriate value!");}

    }
}
