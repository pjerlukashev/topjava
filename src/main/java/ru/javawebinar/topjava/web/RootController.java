package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.web.user.AbstractUserController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RootController extends AbstractUserController {

    @Autowired
    UserProfileToValidator userProfileToFormValidator;

    //Set a form validator
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userProfileToFormValidator);
    }

    @GetMapping("/")
    public String root() {
        return "redirect:meals";
    }

    //    @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/meals")
    public String meals(Model model) {
        return "meals";
    }

    @GetMapping("/profile")
    public String profile() { return "profile"; }

    @PostMapping("/profile")
    public String updateProfile(@Validated UserTo userTo, BindingResult result, SessionStatus status, Model model, HttpServletRequest req) {
        if (result.hasErrors()) {
            model.addAttribute("errorInfo", new ErrorInfo(req.getRequestURL(), ErrorType.VALIDATION_ERROR, ValidationUtil.getStringErrorResponse(result)));
          //  model.addAttribute("message", ValidationUtil.getStringErrorResponse(result));
            return "profile";
        } else {
            super.update(userTo, SecurityUtil.authUserId());
            SecurityUtil.get().update(userTo);
            status.setComplete();
            return "redirect:meals";
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Validated UserTo userTo, BindingResult result, SessionStatus status, ModelMap model, HttpServletRequest req) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            model.addAttribute("errorInfo", new ErrorInfo(req.getRequestURL(), ErrorType.VALIDATION_ERROR, ValidationUtil.getStringErrorResponse(result)));
            return "profile";
        } else {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getEmail();
        }
    }
}
